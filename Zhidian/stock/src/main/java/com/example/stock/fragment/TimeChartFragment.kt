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

package com.example.stock.fragment

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.util.Tools
import com.example.stock.R
import com.example.stock.StockActivity
import com.example.stock.StockActivity.Companion.CODE
import com.example.stock.adapter.TimeDataItemCardAdapter
import com.example.stock.card.TimeDataItemCard
import com.example.stock.utils.BarChartUtils
import com.example.stock.utils.CombineChartUtils
import com.example.stock.utils.LineChartUtils
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.LineChart
import java.lang.StringBuilder
import kotlin.math.absoluteValue
import kotlin.random.Random

class TimeChartFragment : Fragment() {

    companion object {
        fun newInstance() = TimeChartFragment()
    }
    private lateinit var mainActivity: Activity
    private lateinit var mainView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_time_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView = view
        mainActivity = requireActivity()
        initChart()
        initSellPanel()
        addSellData()
        initChasePanel()
    }

    private fun addSellData() {
        val result = (activity as StockActivity).viewModel.stockLiveData.value!!
        val quote = result.chase.data.quote
        timeDataItemCardList.clear()
        val size = 8
        for( i in 0 until size ){
            val chose = Random.nextInt()
            val first = String.format("%.2f",quote.current - (Random.nextDouble()%2+2)%2)
            timeDataItemCardList.add(
                if(chose%2==0) TimeDataItemCard("买",first,(Random.nextInt().absoluteValue%100).toString(),"B",Color.parseColor("#E24141"))
                else TimeDataItemCard("卖",first,(Random.nextInt().absoluteValue%100).toString(),"S",Color.parseColor("#21A538"))
            )
        }
        val timeCard = Tools.getTimeCard()
        for( i in 0 until size ){
            val chose = Random.nextInt()
            val first = String.format("%.2f",quote.current - (Random.nextDouble()%2+2)%2)
            val time = StringBuilder().append(Tools.fillWithZero(timeCard.notesHour)).append(":").append(Tools.fillWithZero(timeCard.notesMinute)).toString()
            chaseDataItemCardList. add(
                if(chose%2==0) TimeDataItemCard(time,first,(Random.nextInt().absoluteValue%100).toString(),"B",Color.parseColor("#E24141"))
                else TimeDataItemCard(time,first,(Random.nextInt().absoluteValue%100).toString(),"S",Color.parseColor("#21A538"))
            )
        }
    }

    private val timeDataItemCardList = ArrayList<TimeDataItemCard>()
    private val chaseDataItemCardList = ArrayList<TimeDataItemCard>()
    private lateinit var  sellAdapter: TimeDataItemCardAdapter
    private lateinit var  chaseAdapter: TimeDataItemCardAdapter
    private fun initSellPanel() {
        val timeChartSellRecyclerView = mainView.findViewById<RecyclerView>(R.id.timeChartSellRecyclerView)
        val layoutManager = GridLayoutManager(mainActivity,1)
        sellAdapter = TimeDataItemCardAdapter(mainActivity,timeDataItemCardList)
        timeChartSellRecyclerView.layoutManager = layoutManager
        timeChartSellRecyclerView.adapter = sellAdapter
    }
    private fun initChasePanel() {
        val timeChartChaseRecyclerView = mainView.findViewById<RecyclerView>(R.id.timeChartChaseRecyclerView)
        val layoutManager = GridLayoutManager(mainActivity,1)
        chaseAdapter = TimeDataItemCardAdapter(mainActivity,chaseDataItemCardList)
        timeChartChaseRecyclerView.layoutManager = layoutManager
        timeChartChaseRecyclerView.adapter = chaseAdapter
    }
    private fun initChart() {
        val code = (activity as StockActivity).intent.getStringExtra(CODE)?:""
        val timeLineChart = mainView.findViewById<LineChart>(R.id.timeLineChart)
        LineChartUtils(mainActivity,timeLineChart,code).initSettings()
        val timeBarChart = mainView.findViewById<BarChart>(R.id.timeBarChart)
        BarChartUtils(mainActivity,timeBarChart,code).initSettings()
    }
}