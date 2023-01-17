package com.ltdung.alebeer.flow

sealed class State<out T> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val throwable: Throwable) : State<Nothing>()
}
