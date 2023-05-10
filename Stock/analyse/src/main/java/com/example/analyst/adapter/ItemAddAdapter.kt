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
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.analyst.R
import com.example.analyst.card.AnalystCard
import de.hdodenhof.circleimageview.CircleImageView

class ItemAddAdapter(val context: Context, val itemCardList:List<AnalystCard>):RecyclerView.Adapter<ItemAddAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val groupAddItemIcon = view.findViewById<CircleImageView>(R.id.groupAddItemIcon)
        val groupAddItemName = view.findViewById<TextView>(R.id.groupAddItemName)
        val groupAddItemData = view.findViewById<TextView>(R.id.groupAddItemData)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_add_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }
    override fun getItemCount() = itemCardList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemCardList[position]
        holder.groupAddItemName.text = item.name
        holder.groupAddItemData.text = item.data
        if(item.avatarIconPath.isEmpty()){
            Glide.with(context).load(R.drawable.default_avatar_pic).into(holder.groupAddItemIcon)
        }else{
            val  bitmap = BitmapFactory.decodeFile(item.avatarIconPath)
            Glide.with(context).load(bitmap).into(holder.groupAddItemIcon)
        }
    }
}