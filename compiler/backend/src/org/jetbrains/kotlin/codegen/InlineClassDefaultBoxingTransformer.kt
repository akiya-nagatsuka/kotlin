/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen

import org.jetbrains.kotlin.codegen.optimization.boxing.isInlineClassBoxing
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode
import org.jetbrains.org.objectweb.asm.tree.MethodNode

object InlineClassDefaultBoxingTransformer : MethodTransformer() {
    override fun transform(internalClassName: String, methodNode: MethodNode) {
        val instructions = methodNode.instructions
        for (ins in instructions.toArray()) {
            if (!ins.isInlineClassBoxing()) continue
            val boxIns = ins as MethodInsnNode
            val defaultBoxingIns = MethodInsnNode(boxIns.opcode, boxIns.owner, KotlinTypeMapper.BOX_DEFAULT_JVM__METHOD_NAME, boxIns.desc)
            instructions.set(boxIns, defaultBoxingIns)
        }
    }
}