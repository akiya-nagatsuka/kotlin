// RUN_PIPELINE_TILL: SOURCE
// FILE: Foo.kt
private typealias Attribute = PlatformAttr
internal class PlatformAttr

// FILE: Main.kt
fun main() {
    <!INVISIBLE_REFERENCE("typealias Attribute = PlatformAttr; private; file")!>Attribute<!>()
    PlatformAttr()
}