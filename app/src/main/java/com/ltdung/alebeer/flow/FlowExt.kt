package com.ltdung.alebeer.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectInScope(
    scope: CoroutineScope,
    crossinline action: suspend (value: T) -> Unit
): Job = scope.launch {
    collect { value -> action(value) }
}

fun <T> flowOf(block: suspend () -> T) = flow {
    emit(block())
}

fun <T, R> Flow<State<T>>.mapBoth(
    success: suspend (T) -> R,
    fail: suspend (Throwable) -> R
): Flow<R> =
    map {
        when (it) {
            is State.Success -> success(it.data)
            is State.Error -> {
                fail(it.throwable)
            }
        }
    }
