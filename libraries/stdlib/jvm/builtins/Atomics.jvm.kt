/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.concurrent

/**
 * An [Int] value that may be updated atomically with guaranteed sequential consistent ordering.
 *
*  Instances of [AtomicInt] are represented by [java.util.concurrent.atomic.AtomicInteger].
 * For details about guarantees of volatile accesses and updates of atomics refer to The Java Language Specification (17.4 Memory Model).
 */
@SinceKotlin("2.0") // TODO: SinceKotlin version should be updated to 2.1, when the API version is updated to 2.1
public actual class AtomicInt(value: Int) {
    /**
     * Atomically gets the value of the atomic.
     *
     * Provides sequential consistent ordering guarantees.
     */
    public actual fun load(): Int
    /**
     * Atomically sets the value of the atomic to the [new value][newValue].
     *
     * Provides sequential consistent ordering guarantees.
     */
    public actual fun store(value: Int)

    /**
     * Atomically sets the value to the given [new value][newValue] and returns the old value.
     */
    public actual fun exchange(newValue: Int): Int

    /**
     * Atomically sets the value to the given [new value][newValue] if the current value equals the [expected value][expectedValue],
     * returns true if the operation was successful and false only if the current value was not equal to the expected value.
     *
     * Provides sequential consistent ordering guarantees and cannot fail spuriously.
     *
     * Comparison of values is done by value.
     */
    public actual fun compareAndSet(expectedValue: Int, newValue: Int): Boolean

    /**
     * Atomically sets the value to the given [new value][newValue] if the current value equals the [expected value][expected]
     * and returns the old value in any case.
     *
     * Provides sequential consistent ordering guarantees and cannot fail spuriously.
     *
     * Comparison of values is done by value.
     */
    public actual fun compareAndExchange(expectedValue: Int, newValue: Int): Int

    /**
     * Atomically adds the [given value][delta] to the current value and returns the old value.
     */
    public actual fun fetchAndAdd(delta: Int): Int

    /**
     * Atomically adds the [given value][delta] to the current value and returns the new value.
     */
    public actual fun addAndFetch(delta: Int): Int

    /**
     * Returns the string representation of the underlying [Int] value.
     *
     * This operation does not provide any atomicity guarantees.
     */
    public actual override fun toString(): String
}

/**
 * A [Long] value that may be updated atomically with guaranteed sequential consistent ordering.
 *
 * Instances of [AtomicLong] are represented by [java.util.concurrent.atomic.AtomicLong].
 * For details about guarantees of volatile accesses and updates of atomics refer to The Java Language Specification (17.4 Memory Model).
 */
@SinceKotlin("2.0") // TODO: SinceKotlin version should be updated to 2.1, when the API version is updated to 2.1
public actual class AtomicLong(value: Long) {
    /**
     * Atomically gets the value of the atomic.
     *
     * Provides sequential consistent ordering guarantees.
     */
    public actual fun load(): Long

    /**
     * Atomically sets the value of the atomic to the [new value][newValue].
     *
     * Provides sequential consistent ordering guarantees.
     */
    public actual fun store(value: Long)

    /**
     * Atomically sets the value to the given [new value][newValue] and returns the old value.
     */
    public actual fun exchange(newValue: Long): Long

    /**
     * Atomically sets the value to the given [new value][newValue] if the current value equals the [expected value][expectedValue],
     * returns true if the operation was successful and false only if the current value was not equal to the expected value.
     *
     * Provides sequential consistent ordering guarantees and cannot fail spuriously.
     *
     * Comparison of values is done by value.
     */
    public actual fun compareAndSet(expectedValue: Long, newValue: Long): Boolean

    /**
     * Atomically sets the value to the given [new value][newValue] if the current value equals the [expected value][expected]
     * and returns the old value in any case.
     *
     * Provides sequential consistent ordering guarantees and cannot fail spuriously.
     *
     * Comparison of values is done by value.
     */
    public actual fun compareAndExchange(expectedValue: Long, newValue: Long): Long

    /**
     * Atomically adds the [given value][delta] to the current value and returns the old value.
     */
    public actual fun fetchAndAdd(delta: Long): Long

    /**
     * Atomically adds the [given value][delta] to the current value and returns the new value.
     */
    public actual fun addAndFetch(delta: Long): Long

    /**
     * Returns the string representation of the underlying [Long] value.
     *
     * This operation does not provide any atomicity guarantees.
     */
    public actual override fun toString(): String
}

/**
 * An [Boolean] value that may be updated atomically with guaranteed sequential consistent ordering.
 *
 * Instances of [AtomicBoolean] are represented by [java.util.concurrent.atomic.AtomicBoolean].
 * For details about guarantees of volatile accesses and updates of atomics refer to The Java Language Specification (17.4 Memory Model).
 */
@SinceKotlin("2.0") // TODO: SinceKotlin version should be updated to 2.1, when the API version is updated to 2.1
public actual class AtomicBoolean (value: Boolean) {
    /**
     * Atomically gets the value of the atomic.
     *
     * Provides sequential consistent ordering guarantees.
     */
    public actual fun load(): Boolean

    /**
     * Atomically sets the value of the atomic to the [new value][newValue].
     *
     * Provides sequential consistent ordering guarantees.
     */
    public actual fun store(newValue: Boolean)

    /**
     * Atomically sets the value to the given [new value][newValue] and returns the old value.
     */
    public actual fun exchange(newValue: Boolean): Boolean

    /**
     * Atomically sets the value to the given [new value][newValue] if the current value equals the [expected value][expectedValue],
     * returns true if the operation was successful and false only if the current value was not equal to the expected value.
     *
     * Provides sequential consistent ordering guarantees and cannot fail spuriously.
     *
     * Comparison of values is done by value.
     */
    public actual fun compareAndSet(expectedValue: Boolean, newValue: Boolean): Boolean

    /**
     * Atomically sets the value to the given [new value][newValue] if the current value equals the [expected value][expected]
     * and returns the old value in any case.
     *
     * Provides sequential consistent ordering guarantees and cannot fail spuriously.
     *
     * Comparison of values is done by value.
     */
    public actual fun compareAndExchange(expectedValue: Boolean, newValue: Boolean): Boolean

    /**
     * Returns the string representation of the underlying [Int] value.
     *
     * This operation does not provide any atomicity guarantees.
     */
    public actual override fun toString(): String
}

/**
 * An object reference that may be updated atomically with guaranteed sequential consistent ordering.
 *
 * Instances of [AtomicReference] are represented by [java.util.concurrent.atomic.AtomicReference].
 * For details about guarantees of volatile accesses and updates of atomics refer to The Java Language Specification (17.4 Memory Model).
 */
@SinceKotlin("2.0") // TODO: SinceKotlin version should be updated to 2.1, when the API version is updated to 2.1
public actual class AtomicReference<T> (value: T) {
    /**
     * Atomically gets the value of the atomic.
     *
     * Provides sequential consistent ordering guarantees.
     */
    public actual fun load(): T

    /**
     * Atomically sets the value of the atomic to the [new value][newValue].
     *
     * Provides sequential consistent ordering guarantees.
     */
    public actual fun store(newValue: T)

    /**
     * Atomically sets the value to the given [new value][newValue] and returns the old value.
     */
    public actual fun exchange(newValue: T): T

    /**
     * Atomically sets the value to the given [new value][newValue] if the current value equals the [expected value][expectedValue],
     * returns true if the operation was successful and false only if the current value was not equal to the expected value.
     *
     * Provides sequential consistent ordering guarantees and cannot fail spuriously.
     *
     * Comparison of values is done by value.
     */
    public actual fun compareAndSet(expectedValue: T, newValue: T): Boolean

    /**
     * Atomically sets the value to the given [new value][newValue] if the current value equals the [expected value][expected]
     * and returns the old value in any case.
     *
     * Provides sequential consistent ordering guarantees and cannot fail spuriously.
     *
     * Comparison of values is done by value.
     */
    public actual fun compareAndExchange(expectedValue: T, newValue: T): T

    /**
     * Returns the string representation of the underlying object.
     *
     * This operation does not provide any atomicity guarantees.
     */
    public actual override fun toString(): String
}