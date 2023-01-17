package com.ltdung.alebeer.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeerModel(
    var id: Int = 0,
    var name: String = "",
    var price: String = "",
    var image: String = "",
    var note: String = "",
    var isFavorite: Boolean = false
) : Parcelable
