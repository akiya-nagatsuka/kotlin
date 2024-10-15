// RUN_PIPELINE_TILL: FIR
// WITH_KOTLIN_JVM_ANNOTATIONS
// LANGUAGE:+DirectJavaActualization
// WITH_STDLIB

// MODULE: m1-common
// FILE: common.kt
expect class Foo {
}

// MODULE: m2-jvm()()(m1-common)
// FILE: Foo.java
@kotlin.annotations.jvm.KotlinActual public class Foo extends Base {
}

// FILE: jvm.kt
open class Base {
    fun foo(a: Int = 1) {}
}
