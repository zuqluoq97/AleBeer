package com.ltdung.alebeer.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ltdung.alebeer.databinding.ActivityBeerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BeerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBeerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            pager.adapter = BeerPagerAdapter(this@BeerActivity)

            TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = listOf("BEER", "FAVORITE")[position]
            }.attach()
        }
    }
}
