// RUN_PIPELINE_TILL: SOURCE
// DIAGNOSTICS: -NO_VALUE_FOR_PARAMETER
class Tree<<!REDECLARATION!>T<!>>(T <!SYNTAX!>element<!>, <!SYNTAX!><!>Tree<T><!SYNTAX!><!> left<!SYNTAX!><!>, <!SYNTAX!><!>Tree<T><!SYNTAX!><!> right<!SYNTAX!><!>) {}