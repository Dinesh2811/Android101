package com.dinesh.android.basic.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class TabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<String>()
    private val icons = mutableListOf<Int>()

    fun addFragment(fragment: Fragment, title: String, icon: Int) {
        fragments.add(fragment)
        titles.add(title)
        icons.add(icon)
    }

    fun getTabTitle(position: Int): String {
        return titles[position]
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