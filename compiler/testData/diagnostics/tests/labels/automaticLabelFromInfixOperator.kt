// RUN_PIPELINE_TILL: BACKEND
fun test(x: List<Int>): Int {
    x myMap {
        return@myMap
    }

    return 0
}

fun myMap(x: List<Int>): Int {
    x myMap {
        return<!LABEL_NAME_CLASH!>@myMap<!>
    }

    return 0
}

infix fun List<Int>.myMap(x: () -> Unit) {}
