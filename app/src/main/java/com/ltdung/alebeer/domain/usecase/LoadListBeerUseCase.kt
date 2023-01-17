package com.ltdung.alebeer.domain.usecase

import com.ltdung.alebeer.domain.model.BeerModel
import com.ltdung.alebeer.domain.repository.BeerRepository
import com.ltdung.alebeer.flow.FlowStateUseCase
import com.ltdung.alebeer.flow.Params2
import com.ltdung.alebeer.flow.State
import com.ltdung.alebeer.flow.flowStateOf
import kotlinx.coroutines.flow.Flow

class LoadListBeerUseCase(private val beerRepository: BeerRepository) :
    FlowStateUseCase<Params2<Int, Int>, List<BeerModel>> {

    override fun invoke(input: Params2<Int, Int>): Flow<State<List<BeerModel>>> {
        val (page, limit) = input
        return flowStateOf {
            beerRepository.loadBeers(page, limit)
        }
    }
}
