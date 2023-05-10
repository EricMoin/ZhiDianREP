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

package com.example.analyst.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.analyst.R
import com.example.analyst.card.AnalystCard

class TrackItemCardAdapter(val context: Context,val analystCardList:ArrayList<AnalystCard>):RecyclerView.Adapter<TrackItemCardAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trackItemName = view.findViewById<TextView>(R.id.trackItemName)
        val trackItemData = view.findViewById<TextView>(R.id.trackItemData)
        val trackItemBuyInDate = view.findViewById<TextView>(R.id.trackItemBuyInDate)
        val trackItemBuyInPrice = view.findViewById<TextView>(R.id.trackItemBuyInPrice)
        val trackItemStateRange = view.findViewById<TextView>(R.id.trackItemStateRange)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.track_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }
    override fun getItemCount() = analystCardList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = analystCardList[position]
        holder.trackItemName.text = item.name
        holder.trackItemData.text = item.data
        holder.trackItemBuyInDate.text = item.buyInDate
        holder.trackItemStateRange.text = item.stateRange
    }
}