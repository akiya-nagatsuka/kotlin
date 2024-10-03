/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.test

enum class TargetBackend(
    val isIR: Boolean,
    private val compatibleWithTargetBackend: TargetBackend? = null
) {
    ANY(false),
    JVM_IR(true),
    JVM_IR_SERIALIZE(true, JVM_IR),
    JS_IR(true),
    JS_IR_ES6(true, JS_IR),
    WASM(true),
    WASM_WASI(true),
    ANDROID_IR(true, JVM_IR),
    NATIVE(true);

    val compatibleWith get() = compatibleWithTargetBackend ?: ANY
}
