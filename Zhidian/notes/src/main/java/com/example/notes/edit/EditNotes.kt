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

package com.example.notes.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.notes.R
import com.example.notes.data.NotesData
import com.example.notes.dialog.BubbleDialog
import com.example.notes.model.NotesDataManager
import com.example.notes.util.EditorFocusListener
import com.example.notes.view.RichEditorManager
import com.example.notes.util.Tools
import com.google.android.material.appbar.AppBarLayout
import com.ns.yc.yccustomtextlib.edit.inter.OnHyperEditListener

class EditNotes : AppCompatActivity() {
    companion object{
        const val DIALOG_X = -550
        const val DIALOG_Y = -300
    }
    private lateinit var notesData:NotesData
    private lateinit var bubbleDialog:BubbleDialog
    private lateinit var bubbleListener:View.OnFocusChangeListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_edit)
        setSupportActionBar(findViewById(R.id.notesEditToolBar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        Tools.applyForReadPermission(this)
        Tools.applyForWritePermission(this)
        notesData = NotesDataManager.currentNotesData
        setResult(RESULT_OK)
        initBubbleDialog()
        initToolBar()
        initEditMenu()
        initHeaderData()
        initInfoData()
        initOnHyperEditListener()
    }

    private fun initOnHyperEditListener() {
        val notesEditorManager = findViewById<RichEditorManager>(R.id.notesEditorManager)
        val listener = object :OnHyperEditListener{
            override fun onImageClick(view: View, imagePath: String) {

                Log.d("EditNotes","????")
            }
            override fun onRtImageDelete(imagePath: String?) {

            }
            override fun onImageCloseClick(view: View?) {

            }
        }
        notesEditorManager.setHyperEditListener(listener)
    }

    private fun initInfoData() {
        val notesEditorManager = findViewById<RichEditorManager>(R.id.notesEditorManager)
        notesEditorManager.loadRichEditor()
    }

    private fun initBubbleDialog() {
        bubbleDialog = BubbleDialog(this)
        bubbleListener = View.OnFocusChangeListener { view, focused ->
            if (focused) {
                bubbleDialog.show(view, DIALOG_X, DIALOG_Y)
            } else bubbleDialog.dismiss()
        }
    }

    private fun initHeaderData() {
        val notesEditCompanyName = findViewById<TextView>(R.id.notesCompanyName)
        val notesDataBuyIn = findViewById<EditText>(R.id.notesDataBuyIn)
        val notesDataStopLoss = findViewById<EditText>(R.id.notesDataStopLoss)
        val notesDataSellOut = findViewById<EditText>(R.id.notesDataSellOut)
        val notesDataTakeProfit = findViewById<EditText>(R.id.notesDataTakeProfit)
        notesEditCompanyName.text = notesData.notesHeaderData.companyName
        notesDataBuyIn.text = SpannableStringBuilder(notesData.notesHeaderData.dataBuyIn)
        notesDataBuyIn.addTextChangedListener {
            notesData.notesHeaderData.dataBuyIn = it.toString()
        }
        notesDataStopLoss.text = SpannableStringBuilder(notesData.notesHeaderData.dataStopLoss)
        notesDataStopLoss.addTextChangedListener {
            notesData.notesHeaderData.dataStopLoss = it.toString()
        }
        notesDataSellOut.text= SpannableStringBuilder(notesData.notesHeaderData.dataSellOut)
        notesDataSellOut.addTextChangedListener {
            notesData.notesHeaderData.dataSellOut = it.toString()
        }
        notesDataTakeProfit.text = SpannableStringBuilder(notesData.notesHeaderData.dataTakeProfit)
        notesDataTakeProfit.addTextChangedListener {
            notesData.notesHeaderData.dataTakeProfit = it.toString()
        }
        notesDataBuyIn.onFocusChangeListener = bubbleListener
        notesDataStopLoss.onFocusChangeListener = bubbleListener
        notesDataSellOut.onFocusChangeListener = bubbleListener
        notesDataTakeProfit.onFocusChangeListener = bubbleListener
    }
    private fun initEditMenu() {
        val notesEditMenu = findViewById<LinearLayout>(R.id.notesEditMenu)
        val notesEditInsertImage = findViewById<ImageView>(R.id.notesEditInsertImage)
        notesEditInsertImage.setOnClickListener {
            callAlbum()
        }
        val notesEditFinishButton = findViewById<Button>(R.id.notesEditFinishButton)
        notesEditFinishButton.setOnClickListener {
            val notesEditorManager = findViewById<RichEditorManager>(R.id.notesEditorManager)
            notesEditorManager.saveData()
            val notesEditLastUpdateTime = findViewById<TextView>(R.id.notesEditLastUpdateTime)
            notesEditLastUpdateTime.text = Tools.toExpressTime(notesData.notesInfoDataList.last().lastUpdateTime)
        }
        notesEditMenu.visibility = GONE
        val notesEditorManager = findViewById<RichEditorManager>(R.id.notesEditorManager)
        notesEditorManager.setEditorFocusListener(
            object :EditorFocusListener{
                override fun setFocusStatus(hasFocused: Boolean) {
                    notesEditMenu.visibility = if(hasFocused) VISIBLE else GONE
                }
            }
        )
    }
    private val startActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.data!=null&&it.resultCode==Activity.RESULT_OK){
            it.data?.data.let { uri ->
                if (uri != null) {
                    val notesEditorManager = findViewById<RichEditorManager>(R.id.notesEditorManager)
                    notesEditorManager.insertImage(uri)
                }
            }
        }
    }
    private fun callAlbum() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type="image/*"
        startActivity.launch(intent)
    }
    private fun initToolBar() {
        val notesBackButton = findViewById<Button>(R.id.notesBackButton)
        notesBackButton.setOnClickListener {
            finish()
        }

        val notesEditTitle = findViewById<EditText>(R.id.notesEditTitle)
        notesEditTitle.text = SpannableStringBuilder(notesData.notesHeaderData.title)
        notesEditTitle.addTextChangedListener {
            notesData.notesHeaderData.title = it.toString()
        }
        val notesEditLastUpdateTime = findViewById<TextView>(R.id.notesEditLastUpdateTime)
        notesEditLastUpdateTime.text = Tools.toExpressTime(notesData.notesInfoDataList.last().lastUpdateTime)

        val notesEditTitleCheckBtn = findViewById<Button>(R.id.notesEditTitleCheckBtn)
        notesEditTitleCheckBtn.setOnClickListener {
            val notesEditOperatorAppBar = findViewById<AppBarLayout>(R.id.notesEditOperatorAppBar)
            val notesEditAppBar = findViewById<AppBarLayout>(R.id.notesEditAppBar)
            notesEditAppBar.visibility = GONE
            notesEditOperatorAppBar.visibility = VISIBLE
            val notesEditorManager = findViewById<RichEditorManager>(R.id.notesEditorManager)
            notesEditorManager.toEditableStatus()
            val notesEditMenu = findViewById<LinearLayout>(R.id.notesEditMenu)
            notesEditMenu.visibility = GONE
            Tools.hideKeyboard(this)
        }
        val notesOperatorBackButton = findViewById<Button>(R.id.notesOperatorBackButton)
        notesOperatorBackButton.setOnClickListener {
            val notesEditOperatorAppBar = findViewById<AppBarLayout>(R.id.notesEditOperatorAppBar)
            val notesEditAppBar = findViewById<AppBarLayout>(R.id.notesEditAppBar)
            notesEditAppBar.visibility = VISIBLE
            notesEditOperatorAppBar.visibility = GONE
            val notesEditorManager = findViewById<RichEditorManager>(R.id.notesEditorManager)
            notesEditorManager.toDefaultStatus()
            notesEditorManager.saveData()
        }

        val notesEditTitleRevokeBtn = findViewById<TextView>(R.id.notesEditTitleRevokeBtn)
        notesEditTitleRevokeBtn.setOnClickListener {
            val notesEditorManager = findViewById<RichEditorManager>(R.id.notesEditorManager)
            notesEditorManager.revokeEditor()
        }
    }
}