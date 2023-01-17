package com.ltdung.alebeer.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ltdung.alebeer.R
import com.ltdung.alebeer.databinding.ItemFavoriteBeerBinding
import com.ltdung.alebeer.domain.model.BeerModel
import mva3.adapter.ItemBinder
import mva3.adapter.ItemViewHolder

class FavoriteBeerBinder : ItemBinder<BeerModel, FavoriteBeerBinder.FavoriteBeerViewHolder>() {

    var listener: FavoriteBeerButtonListener? = null

    override fun bindViewHolder(holder: FavoriteBeerViewHolder?, item: BeerModel) {
        holder?.bind(
            item,
            { listener?.onUpdateButtonClicked(item) },
            { listener?.onDeleteButtonClicked(item) }
        )
    }

    override fun createViewHolder(parent: ViewGroup): FavoriteBeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBeerBinding.inflate(inflater, parent, false)

        return FavoriteBeerViewHolder(binding)
    }

    override fun canBindData(item: Any?): Boolean = item is BeerModel

    class FavoriteBeerViewHolder(private val binding: ItemFavoriteBeerBinding) :
        ItemViewHolder<BeerModel>(binding.root) {

        fun bind(item: BeerModel, update: () -> Unit, delete: () -> Unit) = with(binding) {
            tvBeerName.text = item.name
            tvBeerPrice.text = item.price
            Glide.with(binding.root.context)
                .load(item.image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.placeholder)
                .into(binding.ivBeer)

            etNote.setText(item.note)

            bDelete.setOnClickListener {
                delete()
            }

            bUpdate.setOnClickListener {
                item.note = etNote.text.toString()
                update()
            }
        }
    }

    interface FavoriteBeerButtonListener {
        fun onDeleteButtonClicked(beer: BeerModel)
        fun onUpdateButtonClicked(beer: BeerModel)
    }
}
