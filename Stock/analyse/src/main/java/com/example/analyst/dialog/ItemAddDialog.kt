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

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.analyst.R
import com.example.analyst.adapter.ItemAddAdapter
import com.example.analyst.card.AnalystCard
import com.example.analyst.model.AnalystManager

class ItemAddDialog(activity: Activity, themeResId: Int) : Dialog(activity, themeResId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_item_add_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        initDialog()
    }
    private lateinit var adapter: ItemAddAdapter
    private val itemList = ArrayList<AnalystCard>()
    private fun initDialog() {
        val itemRecyclerView = findViewById<RecyclerView>(R.id.itemAddRecyclerView)
        val layoutManager = GridLayoutManager(context,1)
        adapter = ItemAddAdapter(context,itemList)
        itemRecyclerView.layoutManager = layoutManager
        itemRecyclerView.adapter = adapter
        addItem()
    }
    private fun addItem() {

    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isOutOfBounds(context, event)) {
            dismiss()
        }
        return super.onTouchEvent(event)
    }
    private fun isOutOfBounds(context: Context, event: MotionEvent):Boolean{
        val x = event.x
        val y = event.y
        val slop = ViewConfiguration.get(context).scaledWindowTouchSlop
        val decorView = window!!.decorView
        return (x<-slop)||(y<-slop)||(x>(decorView.width+slop+slop))||(y>(decorView.width+slop+slop))
    }
}