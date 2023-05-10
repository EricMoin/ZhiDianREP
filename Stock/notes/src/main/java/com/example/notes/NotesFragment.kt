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

package com.example.notes

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.adapter.NotesCardAdapter
import com.example.notes.card.NotesCard
import com.example.notes.card.TimeCard
import com.example.notes.dialog.DatePickerDialog
import com.example.notes.model.NotesDataManager
import com.example.notes.util.TimeCallBackListener
import com.example.notes.util.Tools
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yanzhenjie.recyclerview.SwipeMenuItem
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import java.util.*
import kotlin.collections.ArrayList

class NotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotesFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.notes_fragment_main, container, false)
    }
    private lateinit var notesActivity: FragmentActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesActivity = requireActivity()
        NotesDataManager.loadDataFromLocal()
        initToolBar()
        initSmartRefresher()
        initNoteList()
        initSelectorMenu()
        initNewButton()
    }
    private var isSearchBoxShowing = false
    private val textWatcher = object:TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun afterTextChanged(content: Editable) {
            if(content.isEmpty()){
                adapter.reload()
                return
            }
            adapter.removeAll()
            adapter.searchByTitle(content.toString())
        }

    }
    private fun initSmartRefresher() {
        val smartRefresher = notesActivity.findViewById<SmartRefreshLayout>(R.id.notesSmartRefresher)
        smartRefresher.setRefreshHeader(ClassicsHeader(notesActivity))
//        smartRefresher.setRefreshFooter(ClassicsFooter(mainActivity))
        smartRefresher.setOnRefreshListener {
            adapter.reload()
            val time = Tools.getTimeCard()
            if(isSearchBoxShowing){
                initSelectorMenu()
                isSearchBoxShowing = false
            }
            val notesDateRangeText = notesActivity.findViewById<TextView>(R.id.notesDateRangeText)
            notesDateRangeText.text = Tools.toTitleTime(time)
            smartRefresher.finishRefresh()
        }
    }

    private fun initDatePicker(view:View) {
        val notesDateRangeText = view.findViewById<TextView>(R.id.notesDateRangeText)
        val notesDateRangeVector = view.findViewById<Button>(R.id.notesDateRangeVector)
        val calendar = Calendar.getInstance()
        val time = Tools.getTimeCard()
        notesDateRangeText.text = Tools.toTitleTime(time)
        val dialog = DatePickerDialog(notesActivity, com.google.android.material.R.style.Theme_Material3_DayNight_Dialog)
        dialog.setTimeListener(
            object : TimeCallBackListener {
                override fun onTimeCallBack(fromTime: TimeCard, toTime: TimeCard) {
                    val datesRange = StringBuilder()
                        .append(fromTime.notesYear)
                        .append(TimeCard.HYPHEN)
                        .append(fromTime.notesMonth)
                        .append(TimeCard.TO)
                        .append(toTime.notesYear)
                        .append(TimeCard.HYPHEN)
                        .append(toTime.notesMonth)
                    val notesDateRangeText = view.findViewById<TextView>(R.id.notesDateRangeText)
                    notesDateRangeText.text = datesRange.toString()
                    adapter.sortByTime(fromTime,toTime)
                    Log.d("NotesFragment","Time is updated")
                }
            }
        )
        notesDateRangeText.setOnClickListener {
            dialog.show()
        }
        notesDateRangeVector.setOnClickListener {
            dialog.show()
        }
    }
    private fun initSearchBox(){
        val notesSubTitleLinear =  notesActivity.findViewById<LinearLayout>(R.id.notesSubTitleLinear)
        val view = LayoutInflater.from(notesActivity).inflate(R.layout.notes_search_menu,notesSubTitleLinear,false)
        handleText(view)
        notesSubTitleLinear.removeAllViews()
        notesSubTitleLinear.addView(view)
    }
    private fun initSelectorMenu(){
        val notesSubTitleLinear =  notesActivity.findViewById<LinearLayout>(R.id.notesSubTitleLinear)
        val view = LayoutInflater.from(notesActivity).inflate(R.layout.notes_selector_menu,notesSubTitleLinear,false)
        val notesSortButton = view.findViewById<Button>(R.id.notesSortButton)
        notesSortButton.setOnClickListener {
            adapter.reverseAll()
        }
        notesSubTitleLinear.removeAllViews()
        initDatePicker(view)
        notesSubTitleLinear.addView(view)
    }
    private fun initToolBar() {
        val notesBackButton = notesActivity.findViewById<Button>(R.id.notesBackButton)
        notesBackButton.setOnClickListener {
            notesActivity.finish()
        }
        val notesSearchButton = notesActivity.findViewById<Button>(R.id.notesSearchButton)
        notesSearchButton.setOnClickListener {
            initSearchBox()
            isSearchBoxShowing = true
        }
        val callBack = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(isSearchBoxShowing){
                    initSelectorMenu()
                    adapter.reload()
                    isSearchBoxShowing = false
                }else{
                    notesActivity.finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callBack)
    }
    private fun handleText(view:View){
        val notesSearchBox = view.findViewById<EditText>(R.id.notesSearchBox)
        notesSearchBox.addTextChangedListener(textWatcher)
    }
    private fun initNewButton() {
        val notesNewButton = notesActivity.findViewById<Button>(R.id.notesNewButton)
        notesNewButton.setOnClickListener {
            adapter.insertNewItem()
        }
    }
    val notesList:ArrayList<NotesCard> = ArrayList<NotesCard>()
    private lateinit var adapter: NotesCardAdapter
    private fun initNoteList() {
        loadNotes()
        val notesRecyclerView:SwipeRecyclerView = notesActivity.findViewById<SwipeRecyclerView>(R.id.notesRecyclerView)
        adapter = NotesCardAdapter(notesActivity,ArrayList(notesList))
        val layoutManager = GridLayoutManager(notesActivity,1)
        initItemButton(notesRecyclerView)
        notesRecyclerView.layoutManager = layoutManager
        notesRecyclerView.adapter = adapter
        adapter.refreshAll()
    }
    private fun initItemButton(notesRecyclerView:SwipeRecyclerView) {
        val itemHeight = 250
        val itemWidth = 250
        val textSize = 19
        notesRecyclerView.setSwipeMenuCreator { _, rightMenu, _ ->
            val toTopButton = SwipeMenuItem(notesActivity)
                .setBackground(R.color.orange)
                .setHeight(itemHeight)
                .setWidth(itemWidth)
                .setText("置顶")
                .setTextTypeface(Typeface.DEFAULT_BOLD)
                .setTextSize(textSize)
                .setTextColorResource(R.color.white)
            val cancelTopButton = SwipeMenuItem(notesActivity)
                .setBackground(R.color.blue)
                .setHeight(itemHeight)
                .setWidth(itemWidth)
                .setText("取消置顶")
                .setTextTypeface(Typeface.DEFAULT_BOLD)
                .setTextColorResource(R.color.white)
            val deleteButton = SwipeMenuItem(notesActivity)
                .setBackground(R.color.red)
                .setHeight(itemHeight)
                .setWidth(itemWidth)
                .setText("删除")
                .setTextTypeface(Typeface.DEFAULT_BOLD)
                .setTextSize(textSize)
                .setTextColorResource(R.color.white)
            rightMenu.addMenuItem(toTopButton)
            rightMenu.addMenuItem(deleteButton)
        }
        notesRecyclerView.setOnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()
            when(menuBridge.position){
                0 -> {
                    Toast.makeText(notesActivity,"置顶",Toast.LENGTH_SHORT).show()
                    adapter.toTop(adapterPosition)
                }
                1 -> {
                    adapter.removeItem(adapterPosition)
                    Toast.makeText(notesActivity,"已删除",Toast.LENGTH_SHORT).show()
                }
            }
        }
        notesRecyclerView.isLongPressDragEnabled = true
        notesRecyclerView.setOnItemMoveListener(
            object : OnItemMoveListener{
                override fun onItemMove(
                    srcHolder: RecyclerView.ViewHolder,
                    targetHolder:RecyclerView.ViewHolder
                ): Boolean {
                    val fromPosition = srcHolder.bindingAdapterPosition
                    val toPosition = targetHolder.bindingAdapterPosition
                    Collections.swap(adapter.getList(), fromPosition, toPosition)
                    adapter.notifyItemMoved(fromPosition, toPosition)
                    return true
                }
                override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder?) {

                }
            }
        )
    }
    private fun loadNotes() {
        notesList.clear()
        notesList.addAll(NotesDataManager.getNotesCardList())
    }
}