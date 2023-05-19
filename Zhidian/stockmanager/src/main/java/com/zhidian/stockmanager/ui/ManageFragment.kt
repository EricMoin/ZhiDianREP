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
import com.zhidian.stockmanager.ui.adapter.ViewPager2Adapter
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
    private val fragmentList = ArrayList<Fragment>()
    private fun initTabLayout() {
        viewModel.getGroupList()
        viewModel.groupLiveData.observe(
            viewLifecycleOwner, Observer {
                    result ->
                if(result.isNotEmpty()){
                    viewModel.groups.clear()
                    viewModel.groups.addAll(result)
                    for( item in viewModel.groups){
                        val fragment = ManageStockFragment.newInstance()
                        val bundle = Bundle()
                        bundle.putString("group_name",item.groupName)
                        fragment.arguments = bundle
                        fragmentList.add(fragment)
                    }
                    val managerTabLayout = mainView.findViewById<TabLayout>(R.id.managerTabLayout)
                    val managerViewPager = mainView.findViewById<ViewPager2>(R.id.managerViewPager)
                    val adapter = ViewPager2Adapter(childFragmentManager,lifecycle, fragmentList)
                    managerViewPager.adapter = adapter
                    managerViewPager.offscreenPageLimit = 1
                    val mediator = TabLayoutMediator(managerTabLayout,managerViewPager,false,true){
                            tab: TabLayout.Tab, i: Int ->
                            val view = layoutInflater.inflate(R.layout.group_tab_view,managerTabLayout,false)
                            val text = view.findViewById<TextView>(R.id.tabText)
                            text.text = viewModel.groups[i].groupName
                            tab.customView = view
                    }.attach()
                }
            }
        )
    }
}