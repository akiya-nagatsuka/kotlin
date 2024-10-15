// RUN_PIPELINE_TILL: SOURCE
// LANGUAGE: +MultiPlatformProjects
// WITH_STDLIB

// MODULE: common
// FILE: common.kt
expect class N

@JvmInline
value class A(val n: <!VALUE_CLASS_HAS_INAPPLICABLE_PARAMETER_TYPE!>N<!>)

expect class U

@JvmInline
value class B(val u: <!VALUE_CLASS_HAS_INAPPLICABLE_PARAMETER_TYPE!>U<!>)

// MODULE: jvm()()(common)
// FILE: jvm.kt
<!ACTUAL_TYPE_ALIAS_TO_NOTHING!>actual typealias <!NO_ACTUAL_CLASS_MEMBER_FOR_EXPECTED_CLASS!>N<!> = Nothing<!>

actual typealias U = Unit
