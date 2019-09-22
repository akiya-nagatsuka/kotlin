/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.ir.expressions.impl

import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrField
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrGetField
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor

class IrGetFieldImpl(
    startOffset: Int,
    endOffset: Int,
    target: IrField,
    type: IrType,
    origin: IrStatementOrigin? = null,
    irSuperQualifier: IrClass? = null
) :
    IrFieldExpressionBase(startOffset, endOffset, target, type, origin, irSuperQualifier),
    IrGetField {

    constructor(
        startOffset: Int, endOffset: Int,
        target: IrField,
        type: IrType,
        receiver: IrExpression?,
        origin: IrStatementOrigin? = null,
        irSuperQualifier: IrClass? = null
    ) : this(startOffset, endOffset, target, type, origin, irSuperQualifier) {
        this.receiver = receiver
    }

    override fun <R, D> accept(visitor: IrElementVisitor<R, D>, data: D): R {
        return visitor.visitGetField(this, data)
    }

    override fun <D> acceptChildren(visitor: IrElementVisitor<Unit, D>, data: D) {
        receiver?.accept(visitor, data)
    }

    override fun <D> transformChildren(transformer: IrElementTransformer<D>, data: D) {
        receiver = receiver?.transform(transformer, data)
    }
}
