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

package com.zhidian.application.logic.utils

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.model.GradientColor


class LineChartUtils(val chart: LineChart) {
    /**
     * 初始化Chart
     */
    private val count = 60
    fun initSettings() {
        //不显示描述内容
        chart.description.isEnabled = false
        chart.setBackgroundColor(Color.WHITE)
        chart.setDrawGridBackground(false)
        chart.isDragEnabled = false
        chart.isDoubleTapToZoomEnabled = false

        val rightAxis = chart.axisRight
        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        rightAxis.setDrawGridLines(true)
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        val leftAxis = chart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        leftAxis.setDrawGridLines(true)
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f
        xAxis.isEnabled = false

        val legend = chart.legend
        legend.isEnabled = false
        val data = generateLineData()
        xAxis.axisMaximum = data.xMax + 0.25f

        chart.data =data
        chart.invalidate()
    }
    private fun generateLineData(): LineData {
        val d = LineData()
        val blue = Color.rgb(42,130,217)
        val yellow = Color.rgb(190,177,99)
        var flag = 0
        repeat(2) {
            val entries = ArrayList<Entry>()
            for (index in 0 until count) entries.add(Entry(index + 0.5f, getRandom(5, 15)))
            val set = LineDataSet(entries,"")
            set.color = if(flag==0) blue else yellow
            set.lineWidth = 1.5f
            set.setDrawCircles(false)
            set.highLightColor = if(flag==0) blue else yellow
            set.fillColor = if(flag==0) blue else yellow
            set.mode = LineDataSet.Mode.LINEAR
            set.setDrawValues(false)
            d.addDataSet(set)
            flag = 1
        }
        return d
    }
    private fun getRandom(start:Int,to:Int):Float{
        return (start..to).random().toFloat()
    }
}