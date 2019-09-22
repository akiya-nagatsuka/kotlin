/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.backend.js.lower

import org.jetbrains.kotlin.backend.common.FileLoweringPass
import org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder
import org.jetbrains.kotlin.ir.backend.js.utils.realOverrideTarget
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.types.*
import org.jetbrains.kotlin.ir.util.*


// Copied and adapted from Kotlin/Native

class AutoboxingTransformer(val context: JsIrBackendContext) : AbstractValueUsageTransformer(context.irBuiltIns), FileLoweringPass {
    override fun lower(irFile: IrFile) {
        irFile.transformChildrenVoid()

        // TODO: Track & insert parents for temporary variables
        irFile.patchDeclarationParents()
    }

    private tailrec fun IrExpression.isGetUnit(): Boolean =
        when(this) {
            is IrContainerExpression ->
                when (val lastStmt = this.statements.lastOrNull()) {
                    is IrExpression -> lastStmt.isGetUnit()
                    else -> false
                }

            is IrGetObjectValue ->
                this.target == irBuiltIns.unitClass

            else -> false
        }

    override fun IrExpression.useAs(type: IrType): IrExpression {

        val actualType = when (this) {
            is IrConstructorCall -> target.returnType
            is IrCall -> {
                val function = this.target
                if (function.let { it is IrSimpleFunction && it.isSuspend }) {
                    irBuiltIns.anyNType
                } else {
                    function.realOverrideTarget.returnType
                }
            }
            is IrGetField -> this.target.type

            is IrTypeOperatorCall -> when (this.operator) {
                IrTypeOperator.IMPLICIT_INTEGER_COERCION ->
                    // TODO: is it a workaround for inconsistent IR?
                    this.typeOperand

                IrTypeOperator.CAST, IrTypeOperator.IMPLICIT_CAST -> context.irBuiltIns.anyNType

                else -> this.type
            }

            is IrGetValue -> {
                val value = this.target
                if (value is IrValueParameter && value.isDispatchReceiver) {
                    irBuiltIns.anyNType
                } else {
                    this.type
                }
            }

            else -> this.type
        }

        // // TODO: Default parameters are passed as nulls and they need not to be unboxed. Fix this
        if (actualType.makeNotNull().isNothing())
            return this

        val expectedType = type

        if (actualType.isUnit() && !expectedType.isUnit()) {
            // Don't materialize Unit if value is known to be proper Unit on runtime
            if (!this.isGetUnit()) {
                val unitValue = JsIrBuilder.buildGetObjectValue(actualType, context.irBuiltIns.unitClass)
                return JsIrBuilder.buildComposite(actualType, listOf(this, unitValue))
            }
        }

        val actualInlinedClass = actualType.getInlinedClass()
        val expectedInlinedClass = expectedType.getInlinedClass()

        // Mimicking behaviour of current JS backend
        // TODO: Revisit
        if (
            (actualType is IrDynamicType && expectedType.makeNotNull().isChar()) ||
            (actualType.makeNotNull().isChar() && expectedType is IrDynamicType)
        ) return this

        val function = when {
            actualInlinedClass == null && expectedInlinedClass == null -> return this
            actualInlinedClass != null && expectedInlinedClass == null -> context.intrinsics.jsBoxIntrinsic
            actualInlinedClass == null && expectedInlinedClass != null -> context.intrinsics.jsUnboxIntrinsic
            else -> return this
        }

        return buildSafeCall(this, actualType, expectedType) { arg ->
            JsIrBuilder.buildCall(
                function,
                expectedType,
                typeArguments = listOf(actualType, expectedType)
            ).also {
                it.putValueArgument(0, arg)
            }
        }
    }

    private fun buildSafeCall(
        arg: IrExpression,
        actualType: IrType,
        resultType: IrType,
        call: (IrExpression) -> IrExpression
    ): IrExpression {
        if (!actualType.isNullable())
            return call(arg)
        return JsIrBuilder.run {
            // TODO: Set parent of local variables
            val tmp = buildVar(actualType, parent = null, initializer = arg)
            val nullCheck = buildIfElse(
                type = resultType,
                cond = buildCall(irBuiltIns.eqeqSymbol).apply {
                    putValueArgument(0, buildGetValue(tmp))
                    putValueArgument(1, buildNull(irBuiltIns.nothingNType))
                },
                thenBranch = buildNull(irBuiltIns.nothingNType),
                elseBranch = call(buildGetValue(tmp))
            )
            buildBlock(
                type = resultType,
                statements = listOf(
                    tmp,
                    nullCheck
                )
            )
        }
    }

    private val IrFunctionAccessExpression.callTarget: IrFunction
        get() = when (this) {
            is IrConstructorCall -> this.target
            is IrDelegatingConstructorCall -> this.target
            is IrCall -> this.target.realOverrideTarget
            else -> TODO(this.render())
        }


    override fun IrExpression.useAsDispatchReceiver(expression: IrFunctionAccessExpression): IrExpression {
        return this.useAsArgument(expression.callTarget.dispatchReceiverParameter!!)
    }

    override fun IrExpression.useAsExtensionReceiver(expression: IrFunctionAccessExpression): IrExpression {
        return this.useAsArgument(expression.callTarget.extensionReceiverParameter!!)
    }

    override fun IrExpression.useAsValueArgument(
        expression: IrFunctionAccessExpression,
        parameter: IrValueParameter
    ): IrExpression {

        return this.useAsArgument(expression.callTarget.valueParameters[parameter.index])
    }


    override fun IrExpression.useAsVarargElement(expression: IrVararg): IrExpression {
        return this.useAs(
            // Do not box primitive inline classes
            if (this.type.isInlined() && !expression.type.isInlined() && !expression.type.isPrimitiveArray())
                irBuiltIns.anyNType
            else
                expression.varargElementType
        )
    }

    private val IrValueParameter.isDispatchReceiver: Boolean
        get() {
            val parent = this.parent
            if (parent is IrClass)
                return true
            if (parent is IrFunction && parent.dispatchReceiverParameter == this)
                return true
            return false
        }

}

