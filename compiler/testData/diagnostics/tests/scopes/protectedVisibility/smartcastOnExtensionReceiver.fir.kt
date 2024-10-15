// RUN_PIPELINE_TILL: BACKEND
abstract class A<T : Any> {
    abstract protected fun T.foo()

    fun bar(x: T?) {
        if (x != null) {
            x.foo()
        }
    }
}
