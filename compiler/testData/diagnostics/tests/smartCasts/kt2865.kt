// RUN_PIPELINE_TILL: SOURCE
operator fun <K, V> MutableMap<K, V>.set(k: K, v: V) {}

fun foo(a: MutableMap<String, String>, x: String?) {
    a[x!!] = <!DEBUG_INFO_SMARTCAST!>x<!>
    a[<!DEBUG_INFO_SMARTCAST!>x<!>] = x<!UNNECESSARY_NOT_NULL_ASSERTION!>!!<!>
}

fun foo1(a: MutableMap<String, String>, x: String?) {
    a[<!TYPE_MISMATCH!>x<!>] = x!!
    a[x<!UNNECESSARY_NOT_NULL_ASSERTION!>!!<!>] = <!DEBUG_INFO_SMARTCAST!>x<!>
}
