// RUN_PIPELINE_TILL: SOURCE
// FIR_DUMP

fun test1(f: String.() -> Unit) {
    (f)<!NO_VALUE_FOR_PARAMETER!>()<!>

    f<!NO_VALUE_FOR_PARAMETER!>()<!>
}

fun test2(f: (Int) -> Int) {
    1.<!UNRESOLVED_REFERENCE!>f<!>(2)

    2.<!NO_RECEIVER_ALLOWED!>(f)<!>(2)
}
