// RUN_PIPELINE_TILL: SOURCE
// DIAGNOSTICS: -UNUSED_PARAMETER
// CHECK_TYPE

fun foo(x: Int): Int = 0

fun foo(x: UInt): String = ""
fun foo(x: UByte): String = ""
fun foo(x: UShort): String = ""
fun foo(x: ULong): String = ""

fun fooByte(x: Byte): Int = 0
fun fooByte(x: UByte): String = ""

fun fooShort(x: Short): Int = 0
fun fooShort(x: UShort): String = ""

fun fooLong(x: Long): Int = 0
fun fooLong(x: ULong): String = ""

fun test() {
    foo(1) checkType { _<Int>() }
    foo(1u) checkType { _<String>() }

    foo(<!SIGNED_CONSTANT_CONVERTED_TO_UNSIGNED!>2147483648<!>) checkType { _<String>() }
    foo(<!INTEGER_OVERFLOW!>2147483647 + 1<!>) checkType { _<Int>() }

    fooByte(1) checkType { _<Int>() }
    fooByte(1u) checkType { _<String>() }

    fooShort(1) checkType { _<Int>() }
    fooShort(1u) checkType { _<String>() }

    fooLong(1) checkType { _<Int>() }
    fooLong(1u) checkType { _<String>() }
}
