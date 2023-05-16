package com.zhidian.application.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhidian.application.R

class GroupDialog(val context:FragmentActivity,val themeId:Int) : Dialog(context,themeId){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        initDialog()
    }
    private fun initDialog() {
        initGroup()
        initAddDialog()
        initMenu()
    }
    private fun initMenu() {
        val dialogCancel = findViewById<TextView>(R.id.dialogCancel)
        dialogCancel.setOnClickListener {
            dismiss()
        }
        val dialogEnsure = findViewById<TextView>(R.id.dialogEnsure)
        dialogEnsure.setOnClickListener {
            saveGroup()
            dismiss()
        }
    }
    private fun saveGroup() {
    }
    private fun initAddDialog() {
        val addDialog = GroupAddDialog(context,themeId)
        val addGroupPanel = findViewById<LinearLayout>(R.id.addGroupPanel)
        addGroupPanel.setOnClickListener {
            addDialog.show()
            dismiss()
        }
    }
    private fun initGroup() {

    }
}