// RUN_PIPELINE_TILL: SOURCE
// StackOverflow
val p = <!TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM_ERROR!>::<!DEBUG_INFO_MISSING_UNRESOLVED!>p<!><!>

fun foo() = <!TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM_ERROR!>::<!DEBUG_INFO_MISSING_UNRESOLVED!>foo<!><!>
