// RUN_PIPELINE_TILL: SOURCE
val a1: Int = <!INITIALIZER_TYPE_MISMATCH!>1L<!>
val a2: Int = <!INITIALIZER_TYPE_MISMATCH!>0x1L<!>
val a3: Int = <!INITIALIZER_TYPE_MISMATCH!>0X1L<!>
val a4: Int = <!INITIALIZER_TYPE_MISMATCH!>0b1L<!>
val a5: Int = <!INITIALIZER_TYPE_MISMATCH!>0B1L<!>
val a6: Long = 1L
val a7: Long = 0x1L
val a8: Long = 0X1L
val a9: Long = 0b1L
val a10: Long = 0B1L