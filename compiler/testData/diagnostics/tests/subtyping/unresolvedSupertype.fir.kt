// RUN_PIPELINE_TILL: SOURCE
interface A1 : <!UNRESOLVED_REFERENCE!>B<!>

interface A2 : <!SUPERTYPE_INITIALIZED_IN_INTERFACE, UNRESOLVED_REFERENCE!>B<!>()

class A3 : <!UNRESOLVED_REFERENCE!>B<!>, <!UNRESOLVED_REFERENCE!>B<!>

enum class A4 : <!UNRESOLVED_REFERENCE!>B<!>
