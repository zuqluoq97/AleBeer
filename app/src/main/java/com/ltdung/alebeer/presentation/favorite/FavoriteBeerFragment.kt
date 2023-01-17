package com.ltdung.alebeer.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ltdung.alebeer.R
import com.ltdung.alebeer.databinding.FragmentFavoriteBinding
import com.ltdung.alebeer.domain.model.BeerModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mva3.adapter.ListSection
import mva3.adapter.MultiViewAdapter

@AndroidEntryPoint
class FavoriteBeerFragment : Fragment() {

    internal val viewModel: FavoriteBeerViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding
    private val beerAdapter: MultiViewAdapter = MultiViewAdapter()
    private val beerSection = ListSection<BeerModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.run {
            getParcelableArrayList<BeerModel>(BEER_LIST_EXTRA)?.let {
                beerSection.addAll(it)
            }
        }

        binding.rvBeer.run {
            beerAdapter.addSection(beerSection)

            val binder = FavoriteBeerBinder()
            binder.listener = object : FavoriteBeerBinder.FavoriteBeerButtonListener {
                override fun onDeleteButtonClicked(beer: BeerModel) {
                    val index = beerSection.data.indexOf(beer)
                    viewModel.deleteFavoriteBeer(index, beer)
                }

                override fun onUpdateButtonClicked(beer: BeerModel) {
                    viewModel.updateFavoriteBeer(beer)
                }
            }
            beerAdapter.registerItemBinders(binder)

            adapter = beerAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        observeData()
        viewModel.loadListFavoriteBeer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val beers = arrayListOf<BeerModel>()
        beers.addAll(beerSection.data)
        outState.putParcelableArrayList(BEER_LIST_EXTRA, beers)
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeDeleteBeer() }
                launch { observeUpdateBeer() }
                launch { observeLoadBeers() }
            }
        }
    }

    private suspend fun observeDeleteBeer() {
        viewModel.deleteBeerUiState.collect { uiState ->
            when (uiState) {
                is FavoriteBeerViewModel.DeleteBeerUiState.Failed -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.delete_beer_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is FavoriteBeerViewModel.DeleteBeerUiState.Success -> {

                }
            }
        }
    }

    private suspend fun observeUpdateBeer() {
        viewModel.updateBeerUiState.collect { uiState ->
            when (uiState) {
                is FavoriteBeerViewModel.UpdateBeerUiState.Failed -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.update_beer_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is FavoriteBeerViewModel.UpdateBeerUiState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.update_beer_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private suspend fun observeLoadBeers() {
        viewModel.loadBeerUiState.collect { uiState ->
            when (uiState) {
                is FavoriteBeerViewModel.LoadBeerUiState.Success -> {
                    beerSection.set(uiState.beers)
                }
                is FavoriteBeerViewModel.LoadBeerUiState.Failed -> {
                    val errorSnackbar = Snackbar.make(
                        requireView(),
                        R.string.load_beers_failed,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    errorSnackbar.setAction(R.string.retry_button) {
                        viewModel.loadListFavoriteBeer()
                    }
                    errorSnackbar.show()
                }
            }
        }
    }

    companion object {
        private const val BEER_LIST_EXTRA = "BEER_LIST_EXTRA"

        fun newInstace(): FavoriteBeerFragment {
            return FavoriteBeerFragment()
        }
    }
}
