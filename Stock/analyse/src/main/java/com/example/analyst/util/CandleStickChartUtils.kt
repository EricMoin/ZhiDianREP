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
import com.example.analyst.dialog.TraceMarkerView
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry


class CandleStickChartUtils(val context:Context,val chart:CandleStickChart,val markerLayoutResource:Int){
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

        //x轴的设置
        val xAxis = chart.xAxis //获取x轴
        xAxis.axisMinimum = 0f
        xAxis.position = XAxis.XAxisPosition.BOTTOM //设置x轴位置
        xAxis.textColor = Color.GRAY //设置x轴字体颜色
        xAxis.textSize = 14f //设置x轴文字字体大小
        xAxis.setDrawGridLines(true) //设置竖向线  网格线
        xAxis.isEnabled = false

        //y轴的设置
        val yAxisLeft = chart.axisLeft
        yAxisLeft.axisMinimum = 0f //设置左侧y轴
        yAxisLeft.textSize = 0f //左侧y轴文字字体大小
        yAxisLeft.isEnabled = false
        val yAxisRight = chart.axisRight //设置右侧y轴
        yAxisRight.isEnabled = false //设置右侧y轴是否可用

        val marker = TraceMarkerView(context,markerLayoutResource)
        //互相绑定，防止超出图表
        marker.chartView  = chart
        chart.marker = marker
        val legend = chart.legend
        legend.isEnabled = false
    }

    /**
     * 设置数据
     * @param yVals
     */
    public fun setCandleStickData(yVals: List<CandleEntry?>?) {
        val candleDataSet = CandleDataSet(yVals, "")
        candleDataSet.valueTextColor = Color.BLACK
        candleDataSet.valueTextSize = 14f
        candleDataSet.shadowColor = Color.DKGRAY //设置影线的颜色
        candleDataSet.shadowWidth = 0.7f //设置影线的宽度
        candleDataSet.shadowColorSameAsCandle = true //设置影线和蜡烛图的颜色一样
        candleDataSet.decreasingColor = Color.rgb(33,165,56) //设置减少色
        candleDataSet.decreasingPaintStyle = Paint.Style.FILL //绿跌，空心描边
        candleDataSet.increasingColor = Color.rgb(226,65,65)//设置增长色
        candleDataSet.increasingPaintStyle = Paint.Style.FILL //设置增长红 实心
        candleDataSet.neutralColor = Color.RED //当天价格不涨不跌（一字线）颜色
        candleDataSet.isHighlightEnabled = true //设置定位线是否可用
        candleDataSet.highLightColor = Color.BLACK //设置定位线的颜色
        candleDataSet.highlightLineWidth = 0.5f //设置定位线的线宽
        candleDataSet.barSpace = 0.3f //0 至1 之间,越小蜡烛图的宽度越宽
        candleDataSet.setDrawValues(false) //设置是否显示蜡烛图上的文字
        val data = CandleData(candleDataSet)
        chart.data = data
    }
}