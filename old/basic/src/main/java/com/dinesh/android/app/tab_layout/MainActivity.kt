package com.dinesh.android.app.tab_layout

import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab_layout)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        // Add tabs
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(FirstFragment(), "Tab 1", R.drawable.baseline_search_24)
        adapter.addFragment(SecondFragment(), "Tab 2", R.drawable.baseline_share_24)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = null
            tab.setIcon(adapter.getTabIcon(position))
        }.attach()
    }
}
