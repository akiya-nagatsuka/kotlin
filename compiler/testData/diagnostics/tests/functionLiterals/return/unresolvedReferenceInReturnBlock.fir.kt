// RUN_PIPELINE_TILL: SOURCE
val a = l@ {
    return@l <!UNRESOLVED_REFERENCE!>r<!>
}

val b = l@ {
    if ("" == "OK") return@l

    return@l <!UNRESOLVED_REFERENCE!>r<!>
}