// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
class Foo<T>

class Bar<T> {
    fun <S : T> takeFoo(foo: Foo<in S>) {}
}

class Out<out P>

interface A<K>
class B : A<Int>

fun <K : Out<A<String>>> main() {
    val foo = Foo<K>()
    Bar<Out<B>>().takeFoo(foo) // error in 1.3.72, no error in 1.4.31
}
