// RUN_PIPELINE_TILL: SOURCE
// LANGUAGE: +ProhibitSimplificationOfNonTrivialConstBooleanExpressions

val nonConstBool = true
const val constBool = false

const val s1 = <!CONST_VAL_WITH_NON_CONST_INITIALIZER!>""" ${ true && false } """<!>
const val s2 = <!NON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION!>""" ${ nonConstBool && false } """<!>
const val s3 = """ ${ constBool && false } """
