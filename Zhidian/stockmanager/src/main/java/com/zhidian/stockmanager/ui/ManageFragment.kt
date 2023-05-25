package com.zhidian.stockmanager.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhidian.stockmanager.R
import com.zhidian.stockmanager.ui.adapter.ManageStockFragmentAdapter
import com.zhidian.stockmanager.ui.groupmanage.ManageGroupViewModel
import com.zhidian.stockmanager.ui.stockmange.ManageStockFragment

class ManageFragment : Fragment() {

    companion object {
        fun newInstance() = ManageFragment()
    }
    private lateinit var mainView:View
    private val viewModel by lazy { ViewModelProvider(this)[ManageGroupViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_manage, container, false)
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGroups()
        viewModel.getGroupList()
    }
    private val fragmentList = ArrayList<Fragment>()
    private fun initTabLayout() {
        viewModel.loadGroups()
        viewModel.getGroupList()
        val managerTabLayout = mainView.findViewById<TabLayout>(R.id.managerTabLayout)
        val managerViewPager = mainView.findViewById<ViewPager2>(R.id.managerViewPager)
        val adapter = ManageStockFragmentAdapter(childFragmentManager,lifecycle, viewModel.groups)
        managerViewPager.adapter = adapter
        viewModel.groupLiveData.observe(
            viewLifecycleOwner, Observer {
                    result ->
                if(result.isNotEmpty()){
                    viewModel.groups.clear()
                    viewModel.groups.addAll(result)
                    Log.d("Fragment","receive result size ${result.size}")
                    TabLayoutMediator(managerTabLayout,managerViewPager,false,true){
                            tab: TabLayout.Tab, i: Int ->
                        val view = layoutInflater.inflate(R.layout.group_tab_view,managerTabLayout,false)
                        val text = view.findViewById<TextView>(R.id.tabText)
                        Log.d("Fragment","position $i")
                        text.text = viewModel.groups[i].groupName
                        tab.customView = view
                    }.attach()
                    adapter.notifyDataSetChanged()
                }
            }
        )
    }
}