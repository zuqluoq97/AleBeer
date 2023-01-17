package com.ltdung.alebeer.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T> flowStateOf(
    suspendFunc: suspend () -> T,
): Flow<State<T>> =
    flowStateOf(flowOf { suspendFunc() })

fun <T> flowStateOf(flow: Flow<T>): Flow<State<T>> =
    flow.map { State.Success(it) }
        .catch<State<T>> {
            emit(State.Error(it))
        }
