package com.zhidian.application.ui.self

import android.content.Intent
import android.content.res.ColorStateList
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhidian.application.R
import com.zhidian.application.ui.adapter.ViewPager2Adapter
import com.zhidian.application.ui.search.SearchActivity
import com.zhidian.stockmanager.ui.StockManagerActivity

class SelfSelectFragment : Fragment() {
    companion object {
        fun newInstance() = SelfSelectFragment()

    }
    val viewModel by lazy { ViewModelProvider(this)[SelfSelectViewModel::class.java] }
    private lateinit var mainView:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_self_select, container, false)
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initTabLayout()
        initTitle()
    }
    private fun initTitle() {
        val selfSearchBox = mainView.findViewById<TextView>(R.id.selfSearchBox)
        selfSearchBox.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initTabLayout() {
        val textList = listOf("当前持有","触发买入/卖出","触发止盈/止损")
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(StockFragment.newInstance())
        fragmentList.add(StockFragment.newInstance())
        fragmentList.add(StockFragment.newInstance())
        val selfTabLayout = mainView.findViewById<TabLayout>(R.id.selfTabLayout)
        val selfViewPager = mainView.findViewById<ViewPager2>(R.id.selfViewPager)
        selfViewPager.adapter = ViewPager2Adapter(childFragmentManager,lifecycle,fragmentList)
        selfViewPager.isUserInputEnabled = true
        selfViewPager.offscreenPageLimit = textList.size
        TabLayoutMediator(selfTabLayout,selfViewPager,false,true){
                tab: TabLayout.Tab, i: Int ->
                val view = layoutInflater.inflate(R.layout.self_tab_view,selfTabLayout,false)
                val text = view.findViewById<TextView>(R.id.tabText)
                text.text = textList[i]
                tab.customView = view
        }.attach()
        val stockManagerBtn = mainView.findViewById<ImageView>(R.id.stockManagerBtn)
        stockManagerBtn.setOnClickListener {
            val intent = Intent(activity,StockManagerActivity::class.java)
            startActivity(intent)
            Log.d("Fragment","运行结束")
        }
    }
}