package com.ltdung.alebeer.presentation.beer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ltdung.alebeer.R
import com.ltdung.alebeer.databinding.ItemBeerBinding
import com.ltdung.alebeer.domain.model.BeerModel
import mva3.adapter.ItemBinder
import mva3.adapter.ItemViewHolder

class BeerBinder : ItemBinder<BeerModel, BeerBinder.BeerViewHolder>() {

    var listener: BeerItemButtonListener? = null

    override fun bindViewHolder(holder: BeerViewHolder?, item: BeerModel) {
        holder?.bind(item) {
            listener?.onSaveButtonClicked(item)
        }
    }

    override fun createViewHolder(parent: ViewGroup): BeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater, parent, false)

        return BeerViewHolder(binding)
    }

    override fun canBindData(item: Any?): Boolean = item is BeerModel

    class BeerViewHolder(private val binding: ItemBeerBinding) :
        ItemViewHolder<BeerModel>(binding.root) {

        fun bind(item: BeerModel, buttonClicked: () -> Unit) = with(binding) {
            tvBeerName.text = item.name
            tvBeerPrice.text = item.price
            Glide.with(binding.root.context)
                .load(item.image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.placeholder)
                .into(binding.ivBeer)

            etNote.setText(item.note)

            if (item.isFavorite) {
                etNote.isEnabled = false
                bSave.visibility = View.GONE
            } else {
                etNote.isEnabled = true
                bSave.visibility = View.VISIBLE
            }

            bSave.setOnClickListener {
                item.note = etNote.text.toString()
                bSave.setText(R.string.saving_button)
                etNote.isEnabled = false
                buttonClicked()
            }
        }
    }

    fun interface BeerItemButtonListener {
        fun onSaveButtonClicked(beer: BeerModel)
    }
}
