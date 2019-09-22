/*
 * Copyright 2010-2017 JetBrains s.r.o.
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

import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.ir.declarations.IrField
import org.jetbrains.kotlin.ir.declarations.IrProperty
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor

class IrPropertyReferenceImpl(
    startOffset: Int,
    endOffset: Int,
    type: IrType,
    override val target: IrProperty,
    override val descriptor: PropertyDescriptor,
    typeArgumentsCount: Int,
    override val field: IrField?,
    override val getter: IrSimpleFunction?,
    override val setter: IrSimpleFunction?,
    origin: IrStatementOrigin? = null
) :
    IrNoArgumentsCallableReferenceBase(startOffset, endOffset, type, typeArgumentsCount, origin),
    IrPropertyReference {

    constructor(
        startOffset: Int,
        endOffset: Int,
        type: IrType,
        target: IrProperty,
        typeArgumentsCount: Int,
        field: IrField?,
        getter: IrSimpleFunction?,
        setter: IrSimpleFunction?,
        origin: IrStatementOrigin? = null
    ) : this(
        startOffset, endOffset, type,
        target, target.descriptor,
        typeArgumentsCount, field, getter, setter, origin
    )

    @Deprecated(message = "Don't use descriptor-based API for IrPropertyReference", level = DeprecationLevel.WARNING)
    constructor(
        startOffset: Int,
        endOffset: Int,
        type: IrType,
        descriptor: PropertyDescriptor,
        typeArgumentsCount: Int,
        field: IrField?,
        getter: IrSimpleFunction?,
        setter: IrSimpleFunction?,
        origin: IrStatementOrigin? = null
    ) : this(
        startOffset, endOffset, type,
        IrPropertySymbolImpl(descriptor),
        typeArgumentsCount, field, getter, setter, origin
    )

    override fun <R, D> accept(visitor: IrElementVisitor<R, D>, data: D): R =
        visitor.visitPropertyReference(this, data)
}