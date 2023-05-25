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

package com.example.analyst.util

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.analyst.R
import com.example.analyst.dialog.TraceMarkerView
import com.example.analyst.model.AnalyzeChartDao
import com.example.analyst.model.AnalyzeChartRepository
import com.example.analyst.network.TrendResponse
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.io.File
import java.lang.Float.max


class CandleStickChartUtils(val context: Context,val code:String,val chart:CandleStickChart){
    /**
     * 常用设置
     */
    public fun initSetting() {
        chart.description.text = ""
        chart.description.textColor = Color.RED
        chart.description.textSize = 8f //设置描述的文字 ,颜色 大小
        chart.setNoDataText("无最新数据") //没数据的时候显示
        chart.setDrawBorders(true) //是否显示边框
        chart.setBorderWidth(0.4f)
        chart.setBorderColor(Color.GRAY)
        chart.animateX(500) //x轴动画
        chart.setTouchEnabled(true) // 设置是否可以触摸
        chart.isDragEnabled = false // 是否可以拖拽
        chart.setScaleEnabled(false) // 是否可以缩放 x和y轴, 默认是true
        chart.isScaleXEnabled = false//是否可以缩放 仅x轴
        chart.isScaleYEnabled = false //是否可以缩放 仅y轴
        chart.setPinchZoom(false) //设置x轴和y轴能否同时缩放。默认是否
        chart.isDoubleTapToZoomEnabled = false //设置是否可以通过双击屏幕放大图表。默认是true
        chart.isHighlightPerDragEnabled = true //能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        chart.isDragDecelerationEnabled = true //拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        chart.dragDecelerationFrictionCoef = 0.99f //与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止

        val marker = TraceMarkerView(context, R.layout.chart_marker_view)
        //互相绑定，防止超出图表
        marker.chartView  = chart
        chart.marker = marker
        //去掉颜色图标
        val legend = chart.legend
        legend.isEnabled = false
        setCandleStickData()
    }
    val dateList = ArrayList<String>()
    private fun setCandleStickData() {
        val result = AnalyzeChartRepository.loadDataFromLocal(code)
        if (result == null) return
        Log.d("utils","size is ${result.kline.size}")
        val values= setAxis(result)

        val set = CandleDataSet(values,"")
        set.valueTextColor = Color.BLACK
        set.valueTextSize = 14f
        set.shadowColor = Color.DKGRAY //设置影线的颜色
        set.shadowWidth = 0.7f //设置影线的宽度
        set.shadowColorSameAsCandle = true //设置影线和蜡烛图的颜色一样
        set.decreasingColor = Color.rgb(33,165,56) //设置减少色
        set.decreasingPaintStyle = Paint.Style.FILL //绿跌,实心
        set.increasingColor = Color.rgb(226,65,65)//设置增长色
        set.increasingPaintStyle = Paint.Style.FILL //设置增长红 实心
        set.neutralColor = Color.RED //当天价格不涨不跌（一字线）颜色
        set.isHighlightEnabled = true //设置定位线是否可用
        set.highLightColor = Color.BLACK //设置定位线的颜色
        set.highlightLineWidth = 0.5f //设置定位线的线宽
        set.barSpace = 0.1f //0 至1 之间,越小蜡烛图的宽度越宽
        set.setDrawValues(false) //设置是否显示蜡烛图上的文字

        //x轴的设置
        val xAxis = chart.xAxis //获取x轴
        xAxis.axisMinimum = set.xMin-4f
        xAxis.axisMaximum = set.xMax+4f
        xAxis.position = XAxis.XAxisPosition.BOTTOM //设置x轴位置
        xAxis.textColor = Color.GRAY //设置x轴字体颜色
        xAxis.textSize = 12f //设置x轴文字字体大小
        xAxis.setDrawGridLines(true) //设置竖向线  网格线
        xAxis.isEnabled = true
        xAxis.valueFormatter = object:ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                if(value.toInt()==0) return dateList[value.toInt()]
                if(value.toInt()==dateList.size+5) return dateList[value.toInt()-5]
                Log.d("utils","value ${value.toInt()} ")
                return ""
            }
        }
        //y轴的设置
        val yAxisLeft = chart.axisLeft
        yAxisLeft.axisMaximum = set.yMax
        yAxisLeft.textSize = 12f //左侧y轴文字字体大小
        yAxisLeft.isEnabled = true
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

        val yAxisRight = chart.axisRight //设置右侧y轴
        yAxisRight.isEnabled = false //设置右侧y轴是否可用

        val data = CandleData(set)
        chart.data = data
        chart.invalidate()
    }

    private fun setAxis(result:TrendResponse):List<CandleEntry>{
        val values = ArrayList<CandleEntry>()
        for(position in result.kline.indices){
            dateList.add( result.kline[position].time )
            val value = result.kline[position]
            values.add(
                CandleEntry(
                    position.toFloat(),
                    value.highest.toFloat(),
                    value.lowest.toFloat(),
                    if (position%2==0) value.opening.toFloat() else value.closing.toFloat(),
                    if (position%2==1) value.opening.toFloat() else value.closing.toFloat()
                )
            )
        }
        return values
    }
}