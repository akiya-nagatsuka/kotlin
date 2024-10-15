// RUN_PIPELINE_TILL: SOURCE
// ISSUE: KT-47382

class B {
    <!EXTENSION_PROPERTY_MUST_HAVE_ACCESSORS_OR_BE_ABSTRACT!>val Any.x1: String<!>

    val Any.x2: String
        get() = ""

    val Any.x3: String
        get() = <!UNRESOLVED_REFERENCE!>field<!>

    <!EXTENSION_PROPERTY_MUST_HAVE_ACCESSORS_OR_BE_ABSTRACT!>var Any.x4: String<!>

    <!EXTENSION_PROPERTY_MUST_HAVE_ACCESSORS_OR_BE_ABSTRACT!>var Any.x5: String<!>
        get() = ""

    <!EXTENSION_PROPERTY_MUST_HAVE_ACCESSORS_OR_BE_ABSTRACT!>var Any.x6: String<!>
        set(_) {}

    var Any.x7: String
        get() = ""
        set(_) {}

    var Any.x8: String
        get() = ""
        set(arg) {
            <!UNRESOLVED_REFERENCE!>field<!> = arg
        }

    <!INAPPLICABLE_LATEINIT_MODIFIER, UNNECESSARY_LATEINIT!>lateinit<!> var Any.x9: String

    constructor(arg: String) {
        x1 = arg
        <!VAL_REASSIGNMENT!>x2<!> = arg
        <!VAL_REASSIGNMENT!>x3<!> = arg
        x4 = arg
        x5 = arg
        x6 = arg
        x7 = arg
        x8 = arg
        x9 = arg
    }
}
