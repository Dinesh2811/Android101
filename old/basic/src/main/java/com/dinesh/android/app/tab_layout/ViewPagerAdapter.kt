package com.dinesh.android.app.tab_layout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<String>()
    private val icons = mutableListOf<Int>()

    fun addFragment(fragment: Fragment, title: String, icon: Int) {
        fragments.add(fragment)
        titles.add(title)
        icons.add(icon)
    }

    fun getTabIcon(position: Int): Int {
        return icons[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
