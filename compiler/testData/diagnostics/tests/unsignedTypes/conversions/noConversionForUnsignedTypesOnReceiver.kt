// RUN_PIPELINE_TILL: SOURCE
fun UInt.fUInt() {}
fun UByte.fUByte() {}
fun UShort.fUShort() {}
fun ULong.fULong() {}

fun test() {
    1.<!UNRESOLVED_REFERENCE_WRONG_RECEIVER!>fUInt<!>()
    1.<!UNRESOLVED_REFERENCE_WRONG_RECEIVER!>fUByte<!>()
    1.<!UNRESOLVED_REFERENCE_WRONG_RECEIVER!>fUShort<!>()
    1.<!UNRESOLVED_REFERENCE_WRONG_RECEIVER!>fULong<!>()

    3000000000 until <!CONSTANT_EXPECTED_TYPE_MISMATCH!>3000000004UL<!>
    0 until <!CONSTANT_EXPECTED_TYPE_MISMATCH!>10u<!>
}
