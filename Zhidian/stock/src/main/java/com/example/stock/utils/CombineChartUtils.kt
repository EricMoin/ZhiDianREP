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

import android.content.Context
import android.graphics.Color
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BubbleData
import com.github.mikephil.charting.data.BubbleDataSet
import com.github.mikephil.charting.data.BubbleEntry
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.Collections
import kotlin.random.Random


class CombineChartUtils(val context: Context, val chart: CombinedChart) {
    /**
     * 初始化Chart
     */
    private val count = 12
    fun initSettings() {
        //不显示描述内容
        chart.description.isEnabled = false
        chart.setBackgroundColor(Color.WHITE)
        chart.setDrawGridBackground(false)
        chart.setDrawBarShadow(false)
        chart.isHighlightFullBarEnabled = false

        // draw bars behind lines

        // draw bars behind lines
        chart.drawOrder = arrayOf(
            DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        )

        //下方的小块图例
//        val l = chart.legend
//        l.isWordWrapEnabled = true
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)

        val rightAxis = chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


        val leftAxis = chart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTH_SIDED
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f

        val data = CombinedData()
        data.setData(generateLineData())
//        data.setData(generateBarData())
//        data.setData(generateBubbleData())
//        data.setData(generateScatterData())
//        data.setData(generateCandleData())

        xAxis.axisMaximum = data.xMax + 0.25f
        chart.data = data
        chart.invalidate()
    }
    private fun generateLineData(): LineData? {
        val d = LineData()
        var flag = 0
        repeat(2) {
            val entries = ArrayList<Entry>()
            for (index in 0 until count) entries.add(Entry(index + 0.5f, getRandom(5, 15)))
            val set = LineDataSet(entries, "Line DataSet")
            set.color = if(flag==0) Color.BLUE else Color.YELLOW
            set.lineWidth = 2.5f
            set.setDrawCircles(false)
            //        set.setCircleColor(Color.rgb(240, 238, 70))
            //        set.circleRadius = 5f
            set.fillColor = if(flag==0) Color.BLUE else Color.YELLOW
            set.mode = LineDataSet.Mode.LINEAR
            set.setDrawValues(true)
            set.valueTextSize = 10f
            set.valueTextColor = if(flag==0) Color.BLUE else Color.YELLOW
            set.axisDependency = YAxis.AxisDependency.LEFT
            d.addDataSet(set)
            flag = 1
        }
        return d
    }

    private fun generateBarData(): BarData? {
        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()
        for (index in 0 until count) {
            entries1.add(BarEntry(0f, getRandom(25, 25)))

            // stacked
            entries2.add(BarEntry(0f, floatArrayOf(getRandom(13, 12), getRandom(13, 12))))
        }
        val set1 = BarDataSet(entries1, "Bar 1")
        set1.color = Color.rgb(60, 220, 78)
        set1.valueTextColor = Color.rgb(60, 220, 78)
        set1.valueTextSize = 10f
        set1.axisDependency = YAxis.AxisDependency.LEFT
        val set2 = BarDataSet(entries2, "")
        set2.stackLabels = arrayOf("Stack 1", "Stack 2")
        set2.setColors(Color.rgb(61, 165, 255), Color.rgb(23, 197, 255))
        set2.valueTextColor = Color.rgb(61, 165, 255)
        set2.valueTextSize = 10f
        set2.axisDependency = YAxis.AxisDependency.LEFT
        val groupSpace = 0.06f
        val barSpace = 0.02f // x2 dataset
        val barWidth = 0.45f // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
        val d = BarData(set1, set2)
        d.barWidth = barWidth

        // make this BarData object grouped
        d.groupBars(0f, groupSpace, barSpace) // start at x = 0
        return d
    }

    private fun generateScatterData(): ScatterData? {
        val d = ScatterData()
        val entries = ArrayList<Entry>()
        var index = 0f
        while (index < count) {
            entries.add(Entry(index + 0.25f, getRandom(10, 55)))
            index += 0.5f
        }
        val set = ScatterDataSet(entries, "Scatter DataSet")
        set.setColors(*ColorTemplate.MATERIAL_COLORS)
        set.scatterShapeSize = 7.5f
        set.setDrawValues(false)
        set.valueTextSize = 10f
        d.addDataSet(set)
        return d
    }

    private fun generateCandleData(): CandleData? {
        val d = CandleData()
        val entries = ArrayList<CandleEntry>()
        var index = 0
        while (index < count) {
            entries.add(CandleEntry(index + 1f, 90f, 70f, 85f, 75f))
            index += 2
        }
        val set = CandleDataSet(entries, "Candle DataSet")
        set.decreasingColor = Color.rgb(142, 150, 175)
        set.shadowColor = Color.DKGRAY
        set.barSpace = 0.3f
        set.valueTextSize = 10f
        set.setDrawValues(false)
        d.addDataSet(set)
        return d
    }

    private fun generateBubbleData(): BubbleData? {
        val bd = BubbleData()
        val entries = ArrayList<BubbleEntry>()
        for (index in 0 until count) {
            val y: Float = (10..105).random().toFloat()
            val size: Float = (100..105).random().toFloat()
            entries.add(BubbleEntry(index + 0.5f, y, size))
        }
        val set = BubbleDataSet(entries, "Bubble DataSet")
        set.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        set.valueTextSize = 10f
        set.valueTextColor = Color.WHITE
        set.highlightCircleWidth = 1.5f
        set.setDrawValues(true)
        bd.addDataSet(set)
        return bd
    }
    private fun getRandom(start:Int,to:Int):Float{
        return (start..to).random().toFloat()
    }
}