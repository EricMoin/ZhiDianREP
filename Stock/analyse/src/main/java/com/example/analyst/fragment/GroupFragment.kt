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

package com.example.analyst.fragment

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.analyst.R
import com.example.analyst.adapter.ItemCardAdapter
import com.example.analyst.card.AnalystCard
import com.example.analyst.listener.ItemLongPressedListener
import com.example.analyst.model.AnalystManager
import com.google.android.material.checkbox.MaterialCheckBox
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import java.util.Collections
import kotlin.concurrent.thread

class GroupFragment : Fragment() {
    companion object{
        const val LABEL = "*"
        const val TITLE = "-"
        const val SELECT_ALL = 1
        const val REFRESH_LIST = 2
        const val ORDER = "ORDER"
        const val CANCEL_FOCUS = "CANCEL_FOCUS"
        const val CANCEL_TRACK = "CANCEL_TRACK"
        fun newInstance() = GroupFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group, container, false)
    }
    private lateinit var groupActivity: Activity
    private lateinit var mainView:View
    private lateinit var adapter: ItemCardAdapter
    private var isSelectAll = false
    private val itemCardList = ArrayList<AnalystCard>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView = view
        groupActivity = requireActivity()
        initSubTitle()
        initItemCardList()
        initItemButton()
        initItemMenu()
    }

    private fun initItemButton() {
        val itemRecyclerView = mainView.findViewById<SwipeRecyclerView>(R.id.itemRecyclerView)
        itemRecyclerView.isLongPressDragEnabled = false
        adapter.setLongPressListener(
            object : ItemLongPressedListener {
                override fun setLongPressedEnabled(enabled: Boolean) {
                    itemRecyclerView.isLongPressDragEnabled = enabled
                }
            }
        )
        itemRecyclerView.setOnItemMoveListener(
            object : OnItemMoveListener {
                override fun onItemMove(
                    srcHolder: RecyclerView.ViewHolder,
                    targetHolder: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPosition = srcHolder.adapterPosition
                    val toPosition = targetHolder.adapterPosition
                    Collections.swap(itemCardList, fromPosition, toPosition)
                    adapter.notifyItemMoved(fromPosition, toPosition)
                    return true
                }
                override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder?) {

                }
            }
        )
    }

    val handler = object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                SELECT_ALL -> {
                    selectAll()
                }
                REFRESH_LIST -> {
                    val order = msg.data.getString(ORDER)?:""
                    adapter.refreshList(order)
                }
            }
        }
    }
    private fun initItemMenu() {
        val groupMenuSelectAll = mainView.findViewById<MaterialCheckBox>(R.id.groupMenuSelectAll)
        groupMenuSelectAll.setOnCheckedChangeListener { compoundButton, b ->
            isSelectAll = b
            thread {
                val msg = Message.obtain()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }
        val groupMenuCancelFocus = mainView.findViewById<TextView>(R.id.groupMenuCancelFocus)
        groupMenuCancelFocus.setOnClickListener {
            thread {
                val msg = Message.obtain()
                msg.what = REFRESH_LIST
                val bundle = Bundle()
                bundle.putString(ORDER, CANCEL_FOCUS)
                msg.data = bundle
                handler.sendMessage(msg)
            }
        }
    }
    private fun selectAll(){
        for( item in itemCardList){
            item.isChecked = isSelectAll
        }
        val size = itemCardList.size
        Log.d("GroupFragment","size is $size")
        adapter.notifyItemRangeChanged(0,size)
    }
    private fun initSubTitle() {
        val groupState = mainView.findViewById<TextView>(R.id.groupState)
        groupState.text = arguments?.getString(GroupFragment.TITLE)?:""
    }
    private fun initItemCardList() {
        val itemRecyclerView = mainView.findViewById<SwipeRecyclerView>(R.id.itemRecyclerView)
        val layoutManager = GridLayoutManager(groupActivity,1)
        adapter = ItemCardAdapter(groupActivity,itemCardList)
        itemRecyclerView.layoutManager = layoutManager
        itemRecyclerView.adapter = adapter
        addItem()
    }
    private fun addItem() {
        val label = arguments?.getString(LABEL)?:""
        itemCardList.addAll( AnalystManager.getAnalystListByLabel(label) )
        val size = itemCardList.size
        adapter.notifyItemRangeInserted(0,size)
    }
}