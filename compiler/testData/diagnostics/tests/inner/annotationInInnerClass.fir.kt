// RUN_PIPELINE_TILL: SOURCE
class Outer {
    inner class Inner {
        annotation <!NESTED_CLASS_NOT_ALLOWED!>class TestNestedAnnotation<!>
    }
}
