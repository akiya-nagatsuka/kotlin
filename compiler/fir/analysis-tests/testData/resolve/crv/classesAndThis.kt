// WITH_STDLIB

//fun stringF(): String = ""
//fun nsf(): String? = "null"

class A(val x: String = "x") {
    fun foo(y: String): A {
        y // local, should not report
        <!RETURN_VALUE_NOT_USED!>x<!> // unused, may have getter
        this // should not report
        return this // used
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as A

        if (x != other.x) return false

        return true
    }
}

