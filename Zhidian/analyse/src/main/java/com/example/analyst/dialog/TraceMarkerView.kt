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

package com.example.analyst.dialog

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.example.analyst.R
import com.example.analyst.card.TimeCard
import com.example.analyst.util.Tools
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class TraceMarkerView(context:Context,layoutResource:Int) :MarkerView(context,layoutResource) {
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        super.refreshContent(entry, highlight)
        val markerOpen = findViewById<TextView>(R.id.markerOpen)
        val markerClose = findViewById<TextView>(R.id.markerClose)
        val markerHighest = findViewById<TextView>(R.id.markerHighest)
        val markerLowest = findViewById<TextView>(R.id.markerLowest)
        val markerDate = findViewById<TextView>(R.id.markerDate)
        val data = (entry as CandleEntry)
        markerOpen.text = data.open.toString()
        markerClose.text = data.close.toString()
        markerHighest.text = data.high.toString()
        markerLowest.text = data.low.toString()
        val time = Tools.getTimeCard()
        val date = StringBuilder()
            .append(time.notesYear)
            .append(TimeCard.HYPHEN)
            .append(time.notesMonth)
            .append(TimeCard.HYPHEN)
            .append(time.notesDay)
            .toString()
        markerDate.text = date
    }
    override fun getOffset(): MPPointF {
        return MPPointF((-width/2).toFloat(), (height/2).toFloat())
    }
}