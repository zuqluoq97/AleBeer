package com.ltdung.alebeer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ltdung.alebeer.data.local.dao.BeerDao
import com.ltdung.alebeer.data.local.model.BeerLocalModel

@Database(entities = [BeerLocalModel::class], version = 1, exportSchema = false)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
}
