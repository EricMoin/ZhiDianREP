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

package com.example.stock.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stock.R
import com.example.stock.card.TimeDataItemCard

class TimeDataItemCardAdapter (val context: Context, val timeDataItemCardList:List<TimeDataItemCard>): RecyclerView.Adapter<TimeDataItemCardAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeDataFirst = view.findViewById<TextView>(R.id.timeDataFirst)
        val timeDataSecond = view.findViewById<TextView>(R.id.timeDataSecond)
        val timeDataThird = view.findViewById<TextView>(R.id.timeDataThird)
        val timeDataLabel = view.findViewById<TextView>(R.id.timeDataLabel)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.time_data_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }
    override fun getItemCount() = timeDataItemCardList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = timeDataItemCardList[position]
        holder.timeDataFirst.text = item.first
        holder.timeDataSecond.text = item.second
        holder.timeDataThird.text = item.third
        if(item.label!=null) {
            holder.timeDataLabel.text = item.label
            holder.timeDataLabel.setTextColor(item.color)
            holder.timeDataLabel.visibility = VISIBLE
        }
    }
}