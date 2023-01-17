package com.ltdung.alebeer.data.repository

import com.ltdung.alebeer.data.local.dao.BeerDao
import com.ltdung.alebeer.data.local.model.BeerLocalModel
import com.ltdung.alebeer.data.remote.BeerService
import com.ltdung.alebeer.domain.model.BeerModel
import com.ltdung.alebeer.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeerRepositoryImpl(
    private val beerService: BeerService,
    private val beerDao: BeerDao
) : BeerRepository {

    override suspend fun loadBeers(page: Int, limit: Int): List<BeerModel> {
        val savedBeers = beerDao.getFavoriteBeers()

        return beerService.getBeers(page, limit).data
            .filter { it.id != null }
            .map {
                val savedBeer = savedBeers.firstOrNull { (id) -> id == it.id }
                BeerModel(
                    id = it.id!!,
                    name = it.name ?: "",
                    price = it.price ?: "",
                    image = it.image ?: "",
                    isFavorite = (savedBeer != null),
                    note = savedBeer?.note ?: ""
                )
            }
    }

    override suspend fun loadListFavoriteBeer(): List<BeerModel> {
        return beerDao.getFavoriteBeers()
            .map {
                BeerModel(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    image = it.image,
                    isFavorite = true,
                    note = it.note
                )
            }
    }

    override fun loadListFavoriteBeerFlow(): Flow<List<BeerModel>> {
        return beerDao.getListFavoriteBeerFlow().map {
            it.map {
                BeerModel(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    image = it.image,
                    isFavorite = true,
                    note = it.note
                )
            }
        }
    }

    override suspend fun saveFavoriteBeer(favoriteBeer: BeerModel) {
        beerDao.insertFavoriteBeer(
            BeerLocalModel(
                id = favoriteBeer.id,
                name = favoriteBeer.name,
                price = favoriteBeer.price,
                image = favoriteBeer.image,
                note = favoriteBeer.note
            )
        )
    }

    override suspend fun updateFavoriteBeer(favoriteBeer: BeerModel) {
        beerDao.updateFavoriteBeer(
            BeerLocalModel(
                id = favoriteBeer.id,
                name = favoriteBeer.name,
                price = favoriteBeer.price,
                image = favoriteBeer.image,
                note = favoriteBeer.note
            )
        )
    }

    override suspend fun deleteFavoriteBeer(favoriteBeer: BeerModel) {
        beerDao.deleteFavoriteBeer(
            BeerLocalModel(
                id = favoriteBeer.id,
                name = favoriteBeer.name,
                price = favoriteBeer.price,
                image = favoriteBeer.image,
                note = favoriteBeer.note
            )
        )
    }
}
