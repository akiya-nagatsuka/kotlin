// IGNORE_BACKEND_K2: JS_IR, JS_IR_ES6
// IGNORING_WASM_FOR_K2
// IGNORE_BACKEND: WASM

class Outer(val x: Any) {
    inner class Inner(
        val fn: () -> String = { x.toString() }
    )
}

fun box() = Outer("OK").Inner().fn()