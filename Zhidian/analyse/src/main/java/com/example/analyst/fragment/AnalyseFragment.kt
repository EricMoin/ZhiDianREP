/*
 *    @Copyright 2023 EricMoin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.analyst.fragment

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.analyst.R
import com.example.analyst.adapter.ViewPage2Adapter
import com.example.analyst.card.TimeCard
import com.example.analyst.model.AnalystManager
import com.example.analyst.util.CandleStickChartUtils
import com.example.analyst.util.Tools
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.data.CandleEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AnalyseFragment : Fragment() {

    companion object {
        fun newInstance() = AnalyseFragment()
        const val CODE = "[CODE]"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analyse, container, false)
    }
    private lateinit var mainView: View
    private lateinit var mainActivity: Activity
    private lateinit var code:String
    private val textList = listOf("分析师","投资高手","好友")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        code = activity?.intent?.getStringExtra(CODE)?:""
        mainView = view
        mainActivity = requireActivity()
        initHeader()
        initTrendButton()
        initHeaderChart()
        initTracker()
    }

    private fun initHeader() {
        val time = Tools.getTimeCard()
        val analyseLastUpdateTime = mainView.findViewById<TextView>(R.id.analyseLastUpdateTime)
        analyseLastUpdateTime.text = Tools.toExpressTime(time)
    }

    private fun initTracker() {
        val analyseTrackContainer = mainView.findViewById<LinearLayout>(R.id.analyseTrackContainer)
        val masterTrack = LayoutInflater.from(mainActivity).inflate(R.layout.analyse_master_track,analyseTrackContainer,false)
        val sellOutTrack = LayoutInflater.from(mainActivity).inflate(R.layout.analyse_master_track,analyseTrackContainer,false)

        initTrackerView(masterTrack,"高手跟踪")
        initTrackerView(sellOutTrack,"卖出跟踪")
        analyseTrackContainer.addView(masterTrack)
        analyseTrackContainer.addView(sellOutTrack)
    }

    private fun initTrendButton() {
        val analyseShortTrendBtn = mainView.findViewById<TextView>(R.id.analyseShortTrendBtn)
        val analyseMiddleTrendBtn = mainView.findViewById<TextView>(R.id.analyseMiddleTrendBtn)
        analyseShortTrendBtn.setOnClickListener {
            analyseShortTrendBtn.setTextColor(Color.rgb(248,140,58))
            analyseMiddleTrendBtn.setTextColor(Color.BLACK)
            analyseShortTrendBtn.setBackgroundResource(R.drawable.short_trend_slc)
            analyseMiddleTrendBtn.setBackgroundResource(R.drawable.short_trend_uslc)
        }
        analyseMiddleTrendBtn.setOnClickListener {
            analyseShortTrendBtn.setTextColor(Color.BLACK)
            analyseMiddleTrendBtn.setTextColor(Color.rgb(248,140,58))
            analyseShortTrendBtn.setBackgroundResource(R.drawable.short_trend_uslc)
            analyseMiddleTrendBtn.setBackgroundResource(R.drawable.short_trend_slc)
        }
    }

    private fun initHeaderChart() {
        val analyseHeaderChart = mainView.findViewById<CandleStickChart>(R.id.analyseHeaderChart)
        CandleStickChartUtils(requireActivity(),code,analyseHeaderChart).initSetting()
    }
    private fun initTrackerView(view:View,title:String) {
        val analyseTrackTitle = view.findViewById<TextView>(R.id.analyseTrackTitle)
        analyseTrackTitle.text = title
        val fragmentList = ArrayList<Fragment>()
        val labelList = listOf(AnalystManager.ANALYST,AnalystManager.MASTER,AnalystManager.FRIEND)
        for(i in 0..2){
            val fragment = TrackFragment.newInstance()
            val bundle = Bundle()
            bundle.putString(TrackFragment.LABEL,labelList[i])
            fragment.arguments = bundle
            fragmentList.add(fragment)
        }
        val analyseTrackTabLayout = view.findViewById<TabLayout>(R.id.analyseTrackTabLayout)
        val analyseTrackViewPager = view.findViewById<ViewPager2>(R.id.analyseTrackViewPager)
        analyseTrackViewPager.adapter = ViewPage2Adapter(childFragmentManager,lifecycle,fragmentList)
        val mediator = TabLayoutMediator(analyseTrackTabLayout,analyseTrackViewPager,true,true){
            tab: TabLayout.Tab, i: Int ->
            tab.text = textList[i]
        }
        mediator.attach()
    }
}