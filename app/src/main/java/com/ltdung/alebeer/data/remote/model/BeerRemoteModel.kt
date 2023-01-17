package com.ltdung.alebeer.data.remote.model

data class BeerRemoteModel(
    val price: String?,
    val name: String?,
    val rating: RatingRemoteModel?,
    val image: String?,
    val id: Int?
)
