package com.ltdung.alebeer.data.local.dao

import androidx.room.*
import com.ltdung.alebeer.data.local.model.BeerLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {
    @Query("SELECT * FROM beer_table")
    fun getFavoriteBeers(): List<BeerLocalModel>

    @Query("SELECT * FROM beer_table")
    fun getListFavoriteBeerFlow(): Flow<List<BeerLocalModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteBeer(favoriteBeer: BeerLocalModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavoriteBeer(favoriteBeer: BeerLocalModel)

    @Delete
    fun deleteFavoriteBeer(favoriteBeer: BeerLocalModel)
}
