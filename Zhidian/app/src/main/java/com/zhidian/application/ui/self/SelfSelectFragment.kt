package com.zhidian.application.ui.self

import android.content.Intent
import android.content.res.ColorStateList
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.zhidian.application.logic.model.SelfSelectViewModel
import com.zhidian.application.ui.adapter.ViewPager2Adapter
import com.zhidian.application.ui.search.SearchActivity
import com.zhidian.stockmanager.ui.StockManagerActivity

class SelfSelectFragment : Fragment() {

    companion object {
        fun newInstance() = SelfSelectFragment()
        const val DEFAULT = 0
        const val SELECTED = 1
    }
    val viewModel by lazy { ViewModelProvider(this)[SelfSelectViewModel::class.java] }
    private lateinit var mainView:View
    private var priceStatus = DEFAULT
    private var rangeStatus = DEFAULT
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
        initSubTitle()
    }

    private fun initSubTitle() {
        val priceSubUp = mainView.findViewById<ImageView>(R.id.priceSubUp)
        val priceSubDown = mainView.findViewById<ImageView>(R.id.priceSubDown)
        val rangeSubUp = mainView.findViewById<ImageView>(R.id.rangeSubUp)
        val rangeSubDown = mainView.findViewById<ImageView>(R.id.rangeSubDown)
        val priceSubText = mainView.findViewById<TextView>(R.id.priceSubText)
        val rangeSubText = mainView.findViewById<TextView>(R.id.rangeSubText)
        val default = ColorStateList.valueOf(resources.getColor(R.color.label_grey))
        val selected = ColorStateList.valueOf(resources.getColor(R.color.orange))
        priceSubText.setOnClickListener {
            rangeSubUp.imageTintList = default
            rangeSubDown.imageTintList = default
            rangeStatus = DEFAULT
            rangeSubText.setTextColor(default)
            priceSubUp.imageTintList = if (priceStatus == DEFAULT) selected else default
            priceSubDown.imageTintList = if(priceStatus == DEFAULT) default else selected
            priceSubText.setTextColor(selected)
            priceStatus = priceStatus xor SELECTED
        }
        rangeSubText.setOnClickListener {
            priceSubUp.imageTintList = default
            priceSubDown.imageTintList = default
            priceStatus = DEFAULT
            priceSubText.setTextColor(default)
            rangeSubUp.imageTintList = if (rangeStatus == DEFAULT) selected else default
            rangeSubDown.imageTintList = if (rangeStatus == DEFAULT) default else selected
            rangeSubText.setTextColor(selected)
            rangeStatus = rangeStatus xor SELECTED
        }
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
        selfViewPager.isUserInputEnabled = false
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
        }
    }
}