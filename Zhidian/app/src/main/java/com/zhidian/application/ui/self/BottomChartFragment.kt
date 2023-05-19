package com.zhidian.application.ui.self

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.zhidian.application.R
import com.zhidian.application.logic.utils.BarChartUtils
import com.zhidian.application.logic.utils.LineChartUtils

class BottomChartFragment: Fragment() {
    companion object{
        fun newInstance() = BottomChartFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_chart_fragment, container, false)
    }
    private lateinit var mainView:View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView = view
        loadChartData()
    }
    private fun loadChartData() {
        val lineChart = mainView.findViewById<LineChart>(R.id.lineChart)
        val barChart = mainView.findViewById<BarChart>(R.id.barChart)
        LineChartUtils(lineChart).initSettings()
        BarChartUtils(barChart).initSettings()
        Log.d("Chart","Running")
    }
}