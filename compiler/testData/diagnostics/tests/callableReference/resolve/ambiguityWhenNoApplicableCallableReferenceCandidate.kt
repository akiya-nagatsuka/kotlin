// RUN_PIPELINE_TILL: SOURCE
// DIAGNOSTICS: -UNUSED_PARAMETER

fun foo(x: Int) {}
fun foo(y: String) {}

fun <T> bar(f: (T) -> Unit) {}

fun test() {
    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>bar<!>(::<!CALLABLE_REFERENCE_RESOLUTION_AMBIGUITY!>foo<!>)
}