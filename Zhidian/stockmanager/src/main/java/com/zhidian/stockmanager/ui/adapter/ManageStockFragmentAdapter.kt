package com.zhidian.stockmanager.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.ui.ManageFragment
import com.zhidian.stockmanager.ui.stockmange.ManageStockFragment

open class ManageStockFragmentAdapter(val fragmentManager: FragmentManager, val lifecycle: Lifecycle,val groups:ArrayList<ManageGroupItem>): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount() = groups.size
    override fun createFragment(position: Int): Fragment {
        val fragment = ManageStockFragment.newInstance()
        val bundle = Bundle()
        bundle.putString("group_name",groups[position].groupName)
        fragment.arguments = bundle
        return fragment
    }
}