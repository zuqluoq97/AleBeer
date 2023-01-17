package com.ltdung.alebeer.presentation.beer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ltdung.alebeer.R
import com.ltdung.alebeer.databinding.FragmentBeerBinding
import com.ltdung.alebeer.domain.model.BeerModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mva3.adapter.ListSection
import mva3.adapter.MultiViewAdapter
import mva3.adapter.util.InfiniteLoadingHelper

@AndroidEntryPoint
class BeerFragment : Fragment() {

    internal val viewModel: BeerViewModel by viewModels()
    private lateinit var binding: FragmentBeerBinding
    private val beerAdapter: MultiViewAdapter = MultiViewAdapter()
    private lateinit var infiniteLoadingHelper: InfiniteLoadingHelper
    private val beerSection = ListSection<BeerModel>()
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.run {
            currentPage = getInt(CURRENT_PAGE_EXTRA, 1)
            getParcelableArrayList<BeerModel>(BEER_LIST_EXTRA)?.let {
                beerSection.addAll(it)
            }
        }

        binding.rvBeer.run {
            beerAdapter.addSection(beerSection)

            infiniteLoadingHelper = object : InfiniteLoadingHelper(this, R.layout.item_loading) {

                override fun onLoadNextPage(page: Int) {
                    currentPage = page + 1
                    viewModel.loadListBeer(currentPage, ITEM_LIMIT)
                }
            }
            beerAdapter.setInfiniteLoadingHelper(infiniteLoadingHelper)

            val binder = BeerBinder()
            binder.listener = BeerBinder.BeerItemButtonListener {
                val index = beerSection.data.indexOf(it)
                viewModel.saveFavoriteBeer(index, it)
            }
            beerAdapter.registerItemBinders(binder)

            adapter = beerAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        observeData()

        viewModel.loadListBeer(currentPage, ITEM_LIMIT)
        viewModel.loadFavoriteBeer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val beers = arrayListOf<BeerModel>()
        beers.addAll(beerSection.data)
        outState.putParcelableArrayList(BEER_LIST_EXTRA, beers)
        outState.putInt(CURRENT_PAGE_EXTRA, currentPage)
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeLoadBeers() }
                launch { observeSaveBeer() }
                launch { observerListFavoriteBeer() }
            }
        }
    }

    private suspend fun observeLoadBeers() {
        viewModel.loadBeerUiState.collect { uiState ->
            when (uiState) {
                is BeerViewModel.LoadBeerUiState.Success -> {
                    val beers = beerSection.data
                    beers.addAll(uiState.beers.distinctBy { it.id })
                    beerSection.set(beers)
                    infiniteLoadingHelper.markCurrentPageLoaded()
                }
                is BeerViewModel.LoadBeerUiState.Failed -> {
                    val errorSnackbar = Snackbar.make(
                        requireView(),
                        R.string.load_beers_failed,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    errorSnackbar.setAction(R.string.retry_button) {
                        viewModel.loadListBeer(currentPage, ITEM_LIMIT)
                    }
                    errorSnackbar.show()
                }
            }
        }
    }

    private suspend fun observeSaveBeer() {
        viewModel.saveBeerUiState.collect { uiState ->
            when (uiState) {
                is BeerViewModel.SaveBeerUiState.Failed -> {
                    beerSection.set(uiState.index, uiState.unSavedBeer)
                }
                is BeerViewModel.SaveBeerUiState.Success -> {
                    beerSection.set(uiState.index, uiState.savedBeer)
                }
            }
        }
    }

    private suspend fun observerListFavoriteBeer() {
        viewModel.loadFavoriteBeerUiState.collect { uiState ->
            if (uiState is BeerViewModel.LoadFavoriteBeerUiState.Success) {
                beerSection.data.forEachIndexed { idx, beer ->
                    val favorite = uiState.favoriteBeers.firstOrNull { it.id == beer.id }
                    beer.isFavorite = (favorite != null)
                    beer.note = favorite?.note ?: ""
                    beerSection.set(idx, beer)
                }
            }
        }
    }

    companion object {
        private const val BEER_LIST_EXTRA = "BEER_LIST_EXTRA"
        private const val CURRENT_PAGE_EXTRA = "CURRENT_PAGE_EXTRA"
        private const val ITEM_LIMIT = 20

        fun newInstace(): BeerFragment {
            return BeerFragment()
        }
    }
}
