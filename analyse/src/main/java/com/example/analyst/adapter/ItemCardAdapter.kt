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
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.analyst.R
import com.example.analyst.card.AnalystCard
import com.example.analyst.fragment.GroupFragment
import com.example.analyst.listener.ItemLongPressedListener
import com.google.android.material.checkbox.MaterialCheckBox

class ItemCardAdapter(val context: Context,val itemCardList:ArrayList<AnalystCard>):RecyclerView.Adapter<ItemCardAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemUserCheckBox = view.findViewById<MaterialCheckBox>(R.id.itemUserCheckBox)
        val itemUserName = view.findViewById<TextView>(R.id.itemUserName)
        val itemUserData = view.findViewById<TextView>(R.id.itemUserData)
        val itemSortButton = view.findViewById<ImageView>(R.id.itemSortButton)
    }
    private lateinit var longPressListener: ItemLongPressedListener
    fun setLongPressListener(listener: ItemLongPressedListener){
        longPressListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemUserCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            val position =holder.adapterPosition
            val item = itemCardList[position]
            item.isChecked = b
        }
        holder.itemSortButton.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    Toast.makeText(context,"按钮按下",Toast.LENGTH_SHORT).show()
                    longPressListener.setLongPressedEnabled(true)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    Toast.makeText(context,"按钮抬起",Toast.LENGTH_SHORT).show()
                    longPressListener.setLongPressedEnabled(false)
                    true
                }
                else -> {true}
            }
        }
        return holder
    }
    public fun refreshList(order:String){
        if(order == GroupFragment.CANCEL_FOCUS){
            val size = itemCardList.size-1
            for(position in size downTo 0){
                val item = itemCardList[position]
                if(item.isChecked){
                    Log.d("ItemCardAdapter","size is $size")
                    Log.d("ItemCardAdapter","position is $position")
                    itemCardList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
    override fun getItemCount() = itemCardList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemCardList[position]
        holder.itemUserCheckBox.isChecked = item.isChecked
        holder.itemUserName.text = item.name
        holder.itemUserData.text = item.data
    }
}