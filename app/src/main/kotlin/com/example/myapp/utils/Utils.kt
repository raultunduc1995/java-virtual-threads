package com.example.myapp.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


inline fun doSafely(runnable: () -> Unit) {
    try {
        runnable.invoke()
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T : AutoCloseable?, R> Array<T>.use(block: (Array<T>) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    var exception: Throwable? = null
    try {
        return block(this)
    } catch (e: Throwable) {
        exception = e
        throw e
    } finally {
        if (exception == null)
            forEach { it?.close() }
        else
            try {
                forEach { it?.close() }
            } catch (closeException: Throwable) {
//                exception.addSuppressed(closeException)
            }
    }
}