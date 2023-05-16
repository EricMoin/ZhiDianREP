package com.zhidian.application.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.FragmentActivity
import com.zhidian.application.R
import com.zhidian.application.logic.listener.GroupListener

class GroupAddDialog(val context: FragmentActivity, val themeId:Int) : Dialog(context,themeId){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_add_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        initDialog()
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
    private lateinit var groupListener:GroupListener
    private fun saveGroup() {

    }

    private fun initDialog() {
        initMenu()
    }
}