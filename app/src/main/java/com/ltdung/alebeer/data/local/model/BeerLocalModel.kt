package com.ltdung.alebeer.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer_table")
data class BeerLocalModel(
    @PrimaryKey
    var id: Int,
    var name: String,
    var price: String,
    var image: String,
    var note: String
)
