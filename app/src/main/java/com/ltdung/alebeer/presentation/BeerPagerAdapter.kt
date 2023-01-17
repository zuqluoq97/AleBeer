package com.ltdung.alebeer.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ltdung.alebeer.presentation.beer.BeerFragment
import com.ltdung.alebeer.presentation.favorite.FavoriteBeerFragment

class BeerPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = PAGER_TABS

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> BeerFragment.newInstace()
            else -> FavoriteBeerFragment.newInstace()
        }

    companion object {
        private const val PAGER_TABS = 2
    }
}
