// MEMBER_NAME_FILTER: property
abstract class S<caret>ubClass: AbstractClass<Int>()

abstract class AbstractClass<T> {
    val T.property = foo()
    abstract fun foo(): T
}
