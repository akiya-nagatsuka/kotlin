// RUN_PIPELINE_TILL: SOURCE
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_EXPRESSION

fun foo() {
    <!ILLEGAL_UNDERSCORE!>1_<!>
    <!ILLEGAL_UNDERSCORE!>0x_f<!>
    <!ILLEGAL_UNDERSCORE!>0X_f<!>
    <!ILLEGAL_UNDERSCORE!>0b_1<!>
    <!ILLEGAL_UNDERSCORE!>0B_1<!>
    <!ILLEGAL_UNDERSCORE!>1.0_<!>
    <!ILLEGAL_UNDERSCORE!>1_.1<!>
    <!ILLEGAL_UNDERSCORE!>1.0_e1<!>
    <!ILLEGAL_UNDERSCORE!>1.0E_1<!>
    <!ILLEGAL_UNDERSCORE!>0Xe_<!>
    <!ILLEGAL_UNDERSCORE!>1.1_e-1_23<!>
    <!ILLEGAL_UNDERSCORE!>1.0e+_0<!>
    <!ILLEGAL_UNDERSCORE!>1.0e-_0<!>
}