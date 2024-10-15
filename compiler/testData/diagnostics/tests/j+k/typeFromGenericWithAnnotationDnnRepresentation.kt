// RUN_PIPELINE_TILL: SOURCE
// LANGUAGE: +JavaTypeParameterDefaultRepresentationWithDNN

// FILE: GenericTypeAnnotationNotNull.java
import org.jetbrains.annotations.NotNull;

public class GenericTypeAnnotationNotNull<T extends @NotNull Object> {
    public T foo(T a){
        return null;
    }
    public JavaBox<T> foo2(JavaBox<T> a) {
        return null;
    }
}

// FILE: GenericTypeAnnotationNullable.java
import org.jetbrains.annotations.Nullable;

public class GenericTypeAnnotationNullable<T extends @Nullable Object> {
    public T foo(T a){
        return null;
    }
    public JavaBox<T> foo2(JavaBox<T> a) {
        return null;
    }
}

// FILE: JavaBox.java
public class JavaBox<T> {
    public JavaBox(T b) {
        a = b;
    }
    public T a;
}

// FILE: Test.kt
fun genericTypeAnnotationCheck(
    a: GenericTypeAnnotationNotNull<String>,
    b: GenericTypeAnnotationNotNull<<!UPPER_BOUND_VIOLATED!>String?<!>>,
    c: GenericTypeAnnotationNullable<String>,
    d: GenericTypeAnnotationNullable<String?>,
    e: KotlinNullableWithDnn<String>) {
    val k: String = a.foo(<!NULL_FOR_NONNULL_TYPE!>null<!>)
    val k2: String = a.foo("")
    val k3: JavaBox<String?> = <!TYPE_MISMATCH!>a.foo2(null)<!>
    val k4: JavaBox<String?> = <!TYPE_MISMATCH!>a.<!TYPE_MISMATCH!>foo2(JavaBox(null))<!><!>
    val k5: JavaBox<String> = a.foo2(null)
    val k6: JavaBox<String> = a.foo2(JavaBox(null))

    val k7: String = c.foo(<!NULL_FOR_NONNULL_TYPE!>null<!>)
    val k8: String = c.foo("")
    val k9: JavaBox<String?> = <!TYPE_MISMATCH!>c.foo2(null)<!>
    val k10: JavaBox<String?> = <!TYPE_MISMATCH!>c.<!TYPE_MISMATCH!>foo2(JavaBox(null))<!><!>
    val k11: JavaBox<String> = c.foo2(null)
    val k12: JavaBox<String> = c.foo2(JavaBox(null))


    val k13: String? = d.foo(null)
    val k14: String? = d.foo("")
    val k15: JavaBox<String?> = d.foo2(null)
    val k16: JavaBox<String?> = d.foo2(JavaBox(null))
    val k17: JavaBox<String> = <!TYPE_MISMATCH!>d.foo2(null)<!>
    val k18: JavaBox<String> = <!TYPE_MISMATCH!>d.<!TYPE_MISMATCH!>foo2(JavaBox(null))<!><!>

    val k19: String = e.foo(<!NULL_FOR_NONNULL_TYPE!>null<!>)
    val k20: String = e.foo("")
    val k21: JavaBox<String?> = <!TYPE_MISMATCH!>e.foo2(null)<!>
    val k22: JavaBox<String?> = <!TYPE_MISMATCH!>e.<!TYPE_MISMATCH!>foo2(JavaBox(null))<!><!>
    val k23: JavaBox<String> = e.foo2(null)
    val k24: JavaBox<String> = e.foo2(JavaBox(null))
}

class KotlinNullableWithDnn<T>: GenericTypeAnnotationNullable<T&Any>()
