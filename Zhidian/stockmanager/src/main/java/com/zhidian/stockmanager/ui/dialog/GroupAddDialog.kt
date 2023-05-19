package com.zhidian.stockmanager.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.zhidian.stockmanager.R
import com.zhidian.stockmanager.logic.listener.GroupListener

class GroupAddDialog(val context: FragmentActivity, val themeId:Int) : Dialog(context,themeId){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_add_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        initDialog()
    }
    private lateinit var groupListener: GroupListener
    private fun initDialog() {
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
    fun setGroupListener(listener: GroupListener){
        groupListener = listener
    }
    private fun saveGroup() {
        val groupInput = findViewById<EditText>(R.id.groupInput)
        if(groupInput.text.isNotEmpty()) groupListener.setGroup(groupInput.text.toString())
        else Toast.makeText(context,"分组名不能为空！",Toast.LENGTH_SHORT).show()
    }

}