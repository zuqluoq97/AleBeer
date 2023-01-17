package com.ltdung.alebeer.presentation.beer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ltdung.alebeer.domain.model.BeerModel
import com.ltdung.alebeer.domain.usecase.LoadListBeerUseCase
import com.ltdung.alebeer.domain.usecase.LoadListFavoriteBeerUseCase
import com.ltdung.alebeer.domain.usecase.SaveFavoriteBeerUseCase
import com.ltdung.alebeer.flow.None
import com.ltdung.alebeer.flow.Params2
import com.ltdung.alebeer.flow.collectInScope
import com.ltdung.alebeer.flow.mapBoth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val loadListBeerUseCase: LoadListBeerUseCase,
    private val loadListFavoriteBeerUseCase: LoadListFavoriteBeerUseCase,
    private val saveFavoriteBeerUseCase: SaveFavoriteBeerUseCase,
) : ViewModel() {

    private val _loadFavoriteBeerUiState = MutableSharedFlow<LoadFavoriteBeerUiState>()
    val loadFavoriteBeerUiState: SharedFlow<LoadFavoriteBeerUiState> = _loadFavoriteBeerUiState

    private val _loadBeerUiState = MutableSharedFlow<LoadBeerUiState>()
    val loadBeerUiState: SharedFlow<LoadBeerUiState> = _loadBeerUiState

    private val _saveBeerUiState = MutableSharedFlow<SaveBeerUiState?>()
    val saveBeerUiState: SharedFlow<SaveBeerUiState?> = _saveBeerUiState

    fun loadListBeer(page: Int, limit: Int) {
        loadListBeerUseCase(Params2(page, limit))
            .mapBoth(
                success = { LoadBeerUiState.Success(it) },
                fail = { LoadBeerUiState.Failed }
            )
            .flowOn(Dispatchers.IO)
            .collectInScope(viewModelScope) { _loadBeerUiState.emit(it) }
    }

    fun loadFavoriteBeer() {
        loadListFavoriteBeerUseCase(None())
            .mapBoth(
                success = { LoadFavoriteBeerUiState.Success(it) },
                fail = { LoadFavoriteBeerUiState.Failed }
            )
            .flowOn(Dispatchers.IO)
            .collectInScope(viewModelScope) { _loadFavoriteBeerUiState.emit(it) }
    }

    fun saveFavoriteBeer(index: Int, beer: BeerModel) {
        saveFavoriteBeerUseCase(beer)
            .mapBoth(
                success = {
                    beer.isFavorite = true
                    SaveBeerUiState.Success(index, beer)
                },
                fail = { SaveBeerUiState.Failed(index, beer) }
            )
            .flowOn(Dispatchers.IO)
            .collectInScope(viewModelScope) { _saveBeerUiState.emit(it) }
    }

    sealed class LoadBeerUiState {
        object Failed : LoadBeerUiState()
        data class Success(val beers: List<BeerModel>) : LoadBeerUiState()
    }

    sealed class LoadFavoriteBeerUiState {
        object Failed : LoadFavoriteBeerUiState()
        data class Success(val favoriteBeers: List<BeerModel>) : LoadFavoriteBeerUiState()
    }

    sealed class SaveBeerUiState {
        data class Failed(val index: Int, val unSavedBeer: BeerModel) : SaveBeerUiState()
        data class Success(val index: Int, val savedBeer: BeerModel) : SaveBeerUiState()
    }
}
