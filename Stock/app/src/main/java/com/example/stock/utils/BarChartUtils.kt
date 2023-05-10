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

package com.example.stock.utils

import android.R
import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class BarChartUtils(val context: Context, val chart: BarChart) {
    /**
     * 初始化Chart
     */
    private val count = 80
    public fun initSettings() {
        chart.setDrawBarShadow(false)
        chart.isDragEnabled = false
        chart.isDoubleTapToZoomEnabled = false

        chart.description.isEnabled = false
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)

        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.isEnabled = false

        val leftAxis = chart.axisLeft
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        leftAxis.isEnabled = false

        val rightAxis = chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        rightAxis.isEnabled = false

        val data = generateBarData()

        val groupSpace = 0.00f
        val barSpace = 0.0f // x2 dataset
        val barWidth = 0.10f // x2 dataset
        xAxis.axisMinimum = 0f;
        xAxis.axisMaximum = data.getGroupWidth(groupSpace, barSpace) *count+ 0

        val legend = chart.legend
        legend.isEnabled = false

        chart.data = data
        chart.invalidate()
    }
    private fun generateBarData(): BarData {
        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()
        for (index in 0 until count) {
            entries1.add(BarEntry(0f, getRandom(3, 25)))
            entries2.add(BarEntry(0f, getRandom(3,13)))
        }
        val set1 = BarDataSet(entries1, "")
        set1.color = Color.rgb(81, 136, 94)
        set1.setDrawValues(false)
        val set2 = BarDataSet(entries2, "")
        set2.color = Color.rgb(151, 87, 87)
        set2.setDrawValues(false)
        val groupSpace = 0.00f
        val barSpace = 0.0f // x2 dataset
        val barWidth = 0.10f // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
        val d = BarData(set1, set2)
        d.barWidth = barWidth
        // make this BarData object grouped
        d.groupBars(0f, groupSpace, barSpace) // start at x = 0
        return d
    }
    private fun getRandom(start:Int,to:Int):Float{
        return (start..to).random().toFloat()
    }
}