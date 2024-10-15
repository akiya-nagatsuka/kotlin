// RUN_PIPELINE_TILL: SOURCE
// CHECK_TYPE

fun <T> magic(): T = null!!

class Q {
    private fun <E, F> foo() = {
        class C<G> {
            val e: E = magic()
            val f: F = magic()
            val g: G = magic()
        }
        C<F>()
    }

    private var x = foo<CharSequence, Number>()()

    fun bar() {
        x.e.checkType { _<CharSequence>() }
        x.f.checkType { _<Number>() }
        x.g.checkType { _<Number>() }
    }
}
