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

package com.example.notes.view

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
import androidx.core.widget.NestedScrollView
import com.example.notes.R
import com.example.notes.data.NotesInfoData
import com.example.notes.dialog.ImageDialog
import com.example.notes.model.NotesDataManager
import com.example.notes.util.EditorFocusListener
import com.example.notes.util.NotesImageLoader
import com.example.notes.util.Tools
import com.ns.yc.yccustomtextlib.edit.inter.OnHyperEditListener
import com.ns.yc.yccustomtextlib.edit.manager.HyperManager
import com.ns.yc.yccustomtextlib.edit.view.HyperTextEditor
import com.ns.yc.yccustomtextlib.utils.HyperLibUtils
import kotlin.random.Random

class RichEditorManager(context: Context, attrs: AttributeSet?): NestedScrollView(context, attrs) {
    companion object{
        const val TEXT = 1
        const val TEXT_LABEL = "[text]"
        const val IMAGE = 2
        const val IMAGE_LABEL = "[image]"
        const val LOAD = 0
        const val NEW = 1
    }
    private lateinit var linear: LinearLayout
    private lateinit var nowRichEditor:HyperTextEditor
    private lateinit var mHyperEditListener :OnHyperEditListener
    private lateinit var moreMenu:View
    private lateinit var editorFocusListener:EditorFocusListener
    private val imageDialogThemeId = 0
    private var visibleCount = 0
    private var operationList = ArrayList<Int>()
    init {
        linear = LinearLayout(context,attrs)
        linear.orientation = VERTICAL
        this.addView(linear)
        initListener()
        initHyperImageLoader()
        initMoreMenu()
    }
    private fun initListener() {
        mHyperEditListener= object :OnHyperEditListener{
            override fun onImageClick(view: View, imagePath: String) {
                val dialog = ImageDialog(context as Activity,imagePath, com.google.android.material.R.style.Theme_Material3_DayNight_Dialog)
                dialog.show()
            }
            override fun onRtImageDelete(imagePath: String?) {

            }

            override fun onImageCloseClick(view: View?) {

            }

        }
    }
    private fun initMoreMenu() {
        moreMenu = LayoutInflater.from(context).inflate(R.layout.notes_editor_more_layout,linear,false)
        linear.addView(moreMenu)
        moreMenu.setOnClickListener {
            insertNewEditorView(NotesInfoData(),NEW)
            editorFocusListener.setFocusStatus(true)
        }
    }
    private fun newRichEditor(view:View,notesInfoData: NotesInfoData){
        val richEditor = view.findViewById<HyperTextEditor>(R.id.notesRichEditor)
        richEditor.clearAllLayout()
        for( i in 0 until notesInfoData.content.size ){
            val content = notesInfoData.content[i]
            if(content.contains("[image]")){
                val imagePath = content.replace(IMAGE_LABEL,"")
                richEditor.addImageViewAtIndex(i,imagePath)
            }else{
                val str = content.replace(TEXT_LABEL,"")
                richEditor.addEditTextAtIndex(i,str)
                richEditor.lastFocusEdit.hint = "在这里开始你的第一条笔记~"
            }
            Log.d("RichEditorManager","RichEditor is reading $content")
        }
        richEditor.setOnHyperListener(mHyperEditListener)
        nowRichEditor = richEditor
        linear.addView(view,linear.size-1)
        Log.d("RichEditorManager","RichEditor content size is ${richEditor.buildEditData().size}")
    }
    private fun insertNewEditorView(notesInfoData:NotesInfoData,flag:Int){
        val view = LayoutInflater.from(context).inflate(R.layout.notes_editor_item,linear,false)
        newRichEditor(view,notesInfoData)
        view.tag = Random.nextInt()
        val notesEditorTimeLinear = view.findViewById<LinearLayout>(R.id.notesEditorTimeLinear)
        val notesEditorDelBtn = view.findViewById<Button>(R.id.notesEditorDelBtn)
        notesEditorDelBtn.setOnClickListener {
            if(visibleCount!=1){
                view.visibility = GONE
                --visibleCount
                operationList.add(view.tag as Int)
            }else{
                Toast.makeText(context,"这已经是最后一条笔记啦！",Toast.LENGTH_SHORT).show()
            }
            Log.d("RichEditorManager","visibleCount is $visibleCount")
        }
        nowRichEditor.lastFocusEdit.setOnFocusChangeListener { _, hasFocused ->
            notesEditorTimeLinear.visibility = if (hasFocused) GONE else VISIBLE
            editorFocusListener.setFocusStatus(hasFocused)
            Tools.hideKeyboard(context as Activity)
        }
        val timeCard = view.findViewById<TextView>(R.id.notesEditorCardTime)
        if(flag == NEW){
            notesInfoData.lastUpdateTime = Tools.getTimeCard()
            NotesDataManager.currentNotesData.notesInfoDataList.add(notesInfoData)
        }
        timeCard.text = Tools.toExpressTime(notesInfoData.lastUpdateTime)
        ++visibleCount
    }
    fun loadRichEditor(){
        val notesData = NotesDataManager.currentNotesData
        for( notesInfoData in notesData.notesInfoDataList ){
            insertNewEditorView(notesInfoData,LOAD)
        }
        linear.clearFocus()
    }
    private fun initHyperImageLoader() = HyperManager.getInstance().setImageLoader(NotesImageLoader(context))
    fun toEditableStatus(){
        linear.clearFocus()
        val size = linear.size -1
        for(i in 0 until size){
            val view = linear[i]
            if(view.visibility == VISIBLE){
                val notesEditorTimeLinear = view.findViewById<LinearLayout>(R.id.notesEditorTimeLinear)
                val notesEditorDelBtn = view.findViewById<Button>(R.id.notesEditorDelBtn)
                notesEditorTimeLinear.visibility = VISIBLE
                notesEditorDelBtn.visibility = VISIBLE
            }
        }
        moreMenu.visibility = GONE
    }
    fun toDefaultStatus(){
        linear.clearFocus()
        val size = linear.size -1
        for(i in 0 until size){
            val view = linear[i]
            if(view.visibility == VISIBLE){
                val notesEditorDelBtn = view.findViewById<Button>(R.id.notesEditorDelBtn)
                notesEditorDelBtn.visibility = GONE
            }
        }
        moreMenu.visibility = VISIBLE
    }
    fun revokeEditor() {
        if(operationList.isNotEmpty()){
            val tag = operationList.removeLast()
            val view = linear.findViewWithTag<View>(tag)
            view.visibility = VISIBLE
            Log.d("RichEditorManager","RichEditor tag is $tag")
            ++visibleCount
        }
    }
    fun saveData(){
        Toast.makeText(context,"笔记保存中...",Toast.LENGTH_SHORT).show()
        updateTime()
        val notesData = NotesDataManager.currentNotesData
        val oldInfoDataList = ArrayList<NotesInfoData>(notesData.notesInfoDataList)
        notesData.notesInfoDataList.clear()

        val length = linear.size-1
        for(i in 0 until length) {
            val view = linear[i]
            if (view.visibility == GONE) continue
            val richEditor = view.findViewById<HyperTextEditor>(R.id.notesRichEditor)
            val list = richEditor.buildEditData()
            val notesInfoData = NotesInfoData()
            notesInfoData.content.clear()
            for (data in list) {
                val content = StringBuilder()
                when (data.type) {
                    TEXT -> content.append(TEXT_LABEL + data.inputStr)
                    IMAGE -> content.append(IMAGE_LABEL + data.imagePath)
                }
                notesInfoData.content.add(content.toString())
            }
            notesInfoData.lastUpdateTime = oldInfoDataList[i].lastUpdateTime
            notesData.notesInfoDataList.add(notesInfoData)
        }
        NotesDataManager.writeData()
        Toast.makeText(context,"笔记保存完成",Toast.LENGTH_SHORT).show()
    }

    private fun updateTime() {
        linear.clearFocus()
        val notesData = NotesDataManager.currentNotesData
        val timeCard = linear[0].findViewById<TextView>(R.id.notesEditorCardTime)
        val time = Tools.getTimeCard()
        notesData.notesInfoDataList.last().lastUpdateTime = time
        Log.d("RichEditorManager","${Tools.toExpressTime(notesData.notesInfoDataList.last().lastUpdateTime)}")
        timeCard.text = Tools.toExpressTime(time)
    }
    fun insertImage(uri: Uri) {
        val imagePath = HyperLibUtils.getFilePathFromUri(context,uri)
        nowRichEditor.insertImage(imagePath)
    }
    fun setHyperEditListener(listener:OnHyperEditListener){
        mHyperEditListener = listener
    }
    fun setEditorFocusListener(listener:EditorFocusListener){
        editorFocusListener = listener
    }
}