package com.ltdung.alebeer.domain.repository

import com.ltdung.alebeer.domain.model.BeerModel
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun loadBeers(page: Int, Limit: Int): List<BeerModel>
    suspend fun loadListFavoriteBeer(): List<BeerModel>
    fun loadListFavoriteBeerFlow(): Flow<List<BeerModel>>
    suspend fun saveFavoriteBeer(favoriteBeer: BeerModel)
    suspend fun updateFavoriteBeer(favoriteBeer: BeerModel)
    suspend fun deleteFavoriteBeer(favoriteBeer: BeerModel)
}
