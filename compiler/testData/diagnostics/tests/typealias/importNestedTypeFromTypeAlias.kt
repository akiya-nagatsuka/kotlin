// RUN_PIPELINE_TILL: SOURCE
// ISSUE: KT-61694

// FILE: a.kt

package c
class C {
    class Nested
}

typealias TA = C

// FILE: b.kt

import c.TA.Nested

val x: Nested? = null
