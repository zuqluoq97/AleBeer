package com.ltdung.alebeer.data.remote.model

data class RemoteModel<T>(
    val status: String?,
    val data: T
)
