// RUN_PIPELINE_TILL: SOURCE
// FIR_IDENTICAL
// LANGUAGE: +NestedClassesInEnumEntryShouldBeInner

enum class E {
    FIRST,
    SECOND {
        <!NESTED_CLASS_NOT_ALLOWED!>class A<!>
    };
}

val foo: Any.() -> Unit = {}

fun f1() = E.FIRST.foo()
fun f2() = E.FIRST.(foo)()
fun f3() = E.SECOND.foo()
fun f4() = E.SECOND.(foo)()
fun f5() = E.SECOND.<!UNRESOLVED_REFERENCE!>A<!>()
