package com.zhidian.application.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.core.content.contentValuesOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhidian.application.R
import com.zhidian.application.logic.utils.BarChartUtils
import com.zhidian.application.logic.utils.LineChartUtils
import com.zhidian.application.logic.utils.Tools
import com.zhidian.application.ui.adapter.ViewPager2Adapter
import com.zhidian.application.ui.self.BottomChartFragment

class BottomChartDialog(val view:View,width:Int,height:Int) : PopupWindow(view,width,height){
    private val textList = listOf("沪深","港股","美股")
    fun initTabLayout(fragment: Fragment) {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add( BottomChartFragment.newInstance() )
        fragmentList.add( BottomChartFragment.newInstance() )
        fragmentList.add( BottomChartFragment.newInstance() )
        val chartTabLayout = view.findViewById<TabLayout>(R.id.chartTabLayout)
        val btomViewPager = view.findViewById<ViewPager2>(R.id.btomViewPager)
        btomViewPager.offscreenPageLimit = 3
        btomViewPager.adapter = ViewPager2Adapter(fragment.childFragmentManager,fragment.lifecycle,fragmentList)
        val mediator = TabLayoutMediator(chartTabLayout,btomViewPager,false,true){
                tab: TabLayout.Tab, i: Int ->
                tab.text = textList[i]
        }.attach()
    }
}