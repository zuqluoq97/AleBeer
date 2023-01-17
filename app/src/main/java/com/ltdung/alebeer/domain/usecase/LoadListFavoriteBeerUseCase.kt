package com.ltdung.alebeer.domain.usecase

import com.ltdung.alebeer.domain.model.BeerModel
import com.ltdung.alebeer.domain.repository.BeerRepository
import com.ltdung.alebeer.flow.FlowStateUseCase
import com.ltdung.alebeer.flow.None
import com.ltdung.alebeer.flow.State
import com.ltdung.alebeer.flow.flowStateOf
import kotlinx.coroutines.flow.Flow

class LoadListFavoriteBeerUseCase(private val beerRepository: BeerRepository) :
    FlowStateUseCase<None, List<BeerModel>> {

    override fun invoke(input: None): Flow<State<List<BeerModel>>> =
        flowStateOf(
            beerRepository.loadListFavoriteBeerFlow()
        )
}
