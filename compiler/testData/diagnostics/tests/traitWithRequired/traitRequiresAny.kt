// RUN_PIPELINE_TILL: SOURCE
// FIR_IDENTICAL
interface AnyTrait : <!INTERFACE_WITH_SUPERCLASS!>Any<!>

class Foo : AnyTrait

class Bar : AnyTrait, Any()
