package com.ltdung.alebeer.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ltdung.alebeer.domain.model.BeerModel
import com.ltdung.alebeer.domain.usecase.DeleteFavoriteBeerUseCase
import com.ltdung.alebeer.domain.usecase.LoadListFavoriteBeerUseCase
import com.ltdung.alebeer.domain.usecase.UpdateFavoriteBeerUseCase
import com.ltdung.alebeer.flow.None
import com.ltdung.alebeer.flow.collectInScope
import com.ltdung.alebeer.flow.mapBoth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FavoriteBeerViewModel @Inject constructor(
    private val loadListFavoriteBeerUseCase: LoadListFavoriteBeerUseCase,
    private val updateFavoriteBeerUseCase: UpdateFavoriteBeerUseCase,
    private val deleteFavoriteBeerUseCase: DeleteFavoriteBeerUseCase
) : ViewModel() {

    private val _loadBeerUiState = MutableSharedFlow<LoadBeerUiState>()
    val loadBeerUiState: SharedFlow<LoadBeerUiState> = _loadBeerUiState

    private val _updateBeerUiState = MutableSharedFlow<UpdateBeerUiState>()
    val updateBeerUiState: SharedFlow<UpdateBeerUiState> = _updateBeerUiState

    private val _deleteBeerUiState = MutableSharedFlow<DeleteBeerUiState>()
    val deleteBeerUiState: SharedFlow<DeleteBeerUiState?> = _deleteBeerUiState

    fun loadListFavoriteBeer() {
        loadListFavoriteBeerUseCase(None())
            .mapBoth(
                success = { LoadBeerUiState.Success(it) },
                fail = { LoadBeerUiState.Failed }
            )
            .flowOn(Dispatchers.IO)
            .collectInScope(viewModelScope) { _loadBeerUiState.emit(it) }
    }

    fun deleteFavoriteBeer(index: Int, beer: BeerModel) {
        deleteFavoriteBeerUseCase(beer)
            .mapBoth(
                success = { DeleteBeerUiState.Success(index) },
                fail = { DeleteBeerUiState.Failed }
            )
            .flowOn(Dispatchers.IO)
            .collectInScope(viewModelScope) { _deleteBeerUiState.emit(it) }
    }

    fun updateFavoriteBeer(beer: BeerModel) {
        updateFavoriteBeerUseCase(beer)
            .mapBoth(
                success = { UpdateBeerUiState.Success },
                fail = { UpdateBeerUiState.Failed }
            )
            .flowOn(Dispatchers.IO)
            .collectInScope(viewModelScope) { _updateBeerUiState.emit(it) }
    }

    sealed class LoadBeerUiState {
        object Failed : LoadBeerUiState()
        data class Success(val beers: List<BeerModel>) : LoadBeerUiState()
    }

    sealed class UpdateBeerUiState {
        object Failed : UpdateBeerUiState()
        object Success : UpdateBeerUiState()
    }

    sealed class DeleteBeerUiState {
        object Failed : DeleteBeerUiState()
        data class Success(val index: Int) : DeleteBeerUiState()
    }
}
