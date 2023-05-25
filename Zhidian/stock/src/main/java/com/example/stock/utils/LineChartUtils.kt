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
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.Log
import com.example.stock.model.SingleStockRepository
import com.example.stock.network.ChartResponse
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.model.GradientColor
import java.io.File
import java.lang.Float.max
import java.lang.Float.min
import kotlin.random.Random


class LineChartUtils(val context: Context, val chart: LineChart,val code:String) {
    /**
     * 初始化Chart
     */
    fun initSettings() {
        //不显示描述内容
        chart.description.isEnabled = false
        chart.setBackgroundColor(Color.WHITE)
        chart.setDrawGridBackground(false)
        chart.isDragEnabled = false
        chart.isDoubleTapToZoomEnabled = false

        val data = generateLineData()
        chart.data = data
        setAxis(data)
        val legend = chart.legend
        legend.isEnabled = false

        chart.invalidate()
    }
    private fun generateLineData(): LineData {
        val d = LineData()
        val blue = Color.rgb(42,130,217)
        val yellow = Color.rgb(190,177,99)
        var flag = 0
        val result = SingleStockRepository.getStock(code)
        if (result!=null){
            val values = result.hour.data.items
            repeat(2) {
                val entries = ArrayList<Entry>()
                if(flag == 0) {
                    for (index in values.indices) {
                        entries.add(Entry(index.toFloat(), values[index].current.toFloat()))
                    }
                }
                if(flag == 1) {
                    var last = values[0].current.toFloat()
                    for (index in values.indices) {
                        val ratio = (values[index].current.toFloat()-last)/last
                        entries.add(Entry(index.toFloat(),ratio))
                        Log.d("utils","ratio is $ratio")
                    }
                }
                val set = LineDataSet(entries,flag.toString())
                set.color = if(flag==0) blue else yellow
                set.lineWidth = 1.5f
                set.setDrawCircles(false)
                set.highLightColor = if(flag==0) blue else yellow
                set.fillColor = if(flag==0) blue else yellow
                set.mode = LineDataSet.Mode.LINEAR
                set.setDrawValues(false)
                if(flag == 0) set.axisDependency = YAxis.AxisDependency.LEFT
                if(flag == 1) set.axisDependency = YAxis.AxisDependency.RIGHT
                d.addDataSet(set)
                flag = 1
            }

        }
        return d
    }
    private fun setAxis(data:LineData){
        val leftAxis = chart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        leftAxis.setDrawGridLines(true)
        leftAxis.axisMinimum = data.dataSets[0].yMin-1f  // this replaces setStartAtZero(true)
        leftAxis.axisMaximum = data.dataSets[0].yMax+1f
        leftAxis.setDrawGridLines(true)
        Log.d("utils","ymin ${data.dataSets[0].yMin} ymax ${data.dataSets[1].yMax}")
        val rightAxis = chart.axisRight
        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        rightAxis.setDrawGridLines(true)
        rightAxis.axisMinimum = data.dataSets[1].yMin-0.01f  // this replaces setStartAtZero(true)
        rightAxis.axisMaximum = data.dataSets[1].yMax+0.01f
        rightAxis.yOffset = 0.5f
        rightAxis.valueFormatter = object :ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                val show = String.format("%.2f",(value*100.0).toFloat())+"%"
                return show
            }
        }
        Log.d("utils","ymin ${data.dataSets[1].yMin} ymax ${data.dataSets[1].yMax}")

        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.axisMinimum = 0f
        xAxis.isEnabled = false

    }
    private fun getRandom(start:Int,to:Int):Float{
        return (start..to).random().toFloat()
    }
}