// RUN_PIPELINE_TILL: BACKEND
// MODULE: m1-common
// FILE: common.kt
expect class Foo {
    class Inner()
}

expect class SeveralInner {
    class Inner1 {
        class Inner2 {
            class Inner3()
        }
    }
}

// MODULE: m2-jvm()()(m1-common)
// FILE: jvm.kt
class FooImpl {
    class Inner
}

actual typealias Foo = FooImpl

class SeveralInnerImpl {
    class Inner1 {
        class Inner2 {
            class Inner3
        }
    }
}

actual typealias SeveralInner = SeveralInnerImpl
