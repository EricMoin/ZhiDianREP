package com.zhidian.stockmanager.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.iterator
import androidx.fragment.app.FragmentActivity
import com.google.android.material.checkbox.MaterialCheckBox
import com.zhidian.stockmanager.R
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem
import com.zhidian.stockmanager.logic.listener.GroupDialogCallback
import com.zhidian.stockmanager.logic.listener.GroupListener

class GroupDialog(val context:FragmentActivity,val themeId:Int) : Dialog(context,themeId){
    private lateinit var mainView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        initDialog()
    }
    private lateinit var groupsCallback:GroupDialogCallback
    private val removeList = ArrayList<ManageGroupItem>()
    fun setCallback(callback: GroupDialogCallback){
        groupsCallback = callback
    }
    fun newGroupView(group:ManageGroupItem):View{
        val groupLinear = findViewById<LinearLayout>(R.id.dialogLinear)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_group_item,groupLinear,false)
        val dialogCheckBox = view.findViewById<MaterialCheckBox>(R.id.dialogCheckBox)
        val dialogGroupName = view.findViewById<TextView>(R.id.dialogGroupName)
        dialogGroupName.text = group.groupName
        dialogCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                removeList.add(group)
            }else{
                removeList.remove(group)
            }
        }
        return view
    }
    fun loadGroups(list: List<ManageGroupItem>) {
        removeList.clear()
        val groupLinear = findViewById<LinearLayout>(R.id.dialogLinear)
        groupLinear.removeAllViews()
        for(group in list){
            val view = newGroupView(group)
            groupLinear.addView(view)
        }
    }
    fun ignoreGroup(groupName:String){
        val groupLinear = findViewById<LinearLayout>(R.id.dialogLinear)
        for(view in groupLinear){
            val dialogGroupName = view.findViewById<TextView>(R.id.dialogGroupName)
            if(dialogGroupName.text == groupName){
                val dialogCheckBox = view.findViewById<MaterialCheckBox>(R.id.dialogCheckBox)
                dialogCheckBox.visibility = INVISIBLE
            }
        }
    }
    private fun initDialog() {
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
            groupsCallback.setSelectedGroups(removeList)
            dismiss()
        }
    }
    private fun initAddDialog() {
        val addDialog = GroupAddDialog(context,themeId)
        val addGroupPanel = findViewById<LinearLayout>(R.id.addGroupPanel)
        addGroupPanel.setOnClickListener {
            addDialog.show()
            addDialog.setGroupListener(
                object :GroupListener{
                    override fun setGroup(group: String) {
                        val groupLinear = findViewById<LinearLayout>(R.id.dialogLinear)
                        val view = newGroupView(ManageGroupItem(group,ArrayList<ManageStockItem>()))
                        groupLinear.addView(view)
                    }
                }
            )
        }
    }
}