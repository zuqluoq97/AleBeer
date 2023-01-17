package com.ltdung.alebeer.domain.usecase

import com.ltdung.alebeer.domain.model.BeerModel
import com.ltdung.alebeer.domain.repository.BeerRepository
import com.ltdung.alebeer.flow.FlowStateUseCase
import com.ltdung.alebeer.flow.State
import com.ltdung.alebeer.flow.flowStateOf
import kotlinx.coroutines.flow.Flow

class UpdateFavoriteBeerUseCase(private val beerRepository: BeerRepository) :
    FlowStateUseCase<BeerModel, Unit> {

    override fun invoke(input: BeerModel): Flow<State<Unit>> =
        flowStateOf {
            beerRepository.updateFavoriteBeer(input)
        }
}
