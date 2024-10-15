// RUN_PIPELINE_TILL: SOURCE
// MODULE: lib1

// FILE: DemoClassInternal1.kt
abstract class DemoClassInternal {
    @PublishedApi
    internal open fun demo(): Int = 1
}

// MODULE: main(lib1)

// FILE: MyDemo.kt
open class MyDemo1 : DemoClassInternal()

class MyDemo2 : MyDemo1()

class MyDemo3 : DemoClassInternal() {
    <!CANNOT_OVERRIDE_INVISIBLE_MEMBER!>override<!> fun demo(): Int = 2
}

class MyDemo4 : DemoClassInternal() {
    fun demo(): Int {
        return super.<!INVISIBLE_MEMBER!>demo<!>()
    }
}

// FILE: Test.kt
fun test() {
    MyDemo1().<!INVISIBLE_MEMBER!>demo<!>()
    MyDemo2().<!INVISIBLE_MEMBER!>demo<!>()
}
