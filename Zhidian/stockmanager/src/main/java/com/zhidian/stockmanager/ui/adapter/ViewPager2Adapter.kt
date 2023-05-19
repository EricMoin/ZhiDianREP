package com.zhidian.stockmanager.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

open class ViewPager2Adapter(val fragmentManager: FragmentManager,val lifecycle: Lifecycle, private val fragmentList:List<Fragment>): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
}