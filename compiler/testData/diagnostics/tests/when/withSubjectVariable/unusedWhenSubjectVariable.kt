// RUN_PIPELINE_TILL: BACKEND
// LANGUAGE: +VariableDeclarationInWhenSubject
// DIAGNOSTICS: +UNUSED_VARIABLE

fun foo(): Any = 42

fun test() {
    when (val <!UNUSED_VARIABLE!>x<!> = foo()) {
    }
}
