package com.zhidian.stockmanager.ui.adapter

import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhidian.stockmanager.R
import com.zhidian.stockmanager.logic.GroupRepository
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.listener.GroupListener
import com.zhidian.stockmanager.logic.utils.StockMoveHelper
import com.zhidian.stockmanager.ui.groupmanage.ManageGroupFragment
import java.io.File

class ManageGroupAdapter(private val fragment:ManageGroupFragment, private val callback: StockMoveHelper, private val groupList:List<ManageGroupItem>): RecyclerView.Adapter<ManageGroupAdapter.ViewHolder>(){
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val groupDelete = view.findViewById<ImageView>(R.id.groupDelete)
        val groupName = view.findViewById<TextView>(R.id.groupName)
        val groupEdit = view.findViewById<ImageView>(R.id.groupEdit)
        val groupSort = view.findViewById<ImageView>(R.id.groupSort)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = fragment.layoutInflater.inflate(R.layout.manage_group_item,parent,false)
        val holder = ViewHolder(view)
        holder.groupDelete.setOnClickListener {
            val position = holder.adapterPosition
            fragment.viewModel.removeGroup(position)
            fragment.viewModel.writeGroups()
            fragment.viewModel.getGroupList()
        }
        holder.groupEdit.setOnClickListener {
            val position = holder.adapterPosition
            fragment.dialog.setGroupListener(
                object : GroupListener {
                    override fun setGroup(groupName: String) {
                        fragment.viewModel.groups[position].groupName = groupName
                        fragment.viewModel.updateGroup(position,fragment.viewModel.groups[position])
                        fragment.viewModel.writeGroups()
                        notifyItemChanged(position)
                    }
                }
            )
            fragment.dialog.show()
        }
        holder.groupSort.setOnLongClickListener {
            callback.setIsLongPressDragEnabled(true)
            //加入短按动作，取消可以长按排序的功能
            false
        }
        holder.groupSort.setOnClickListener {
            callback.setIsLongPressDragEnabled(false)
            //拦截点击，此处结束不穿透
            true
        }
        return holder
    }
    private val defaultList = GroupRepository.getDefaultList()
    override fun getItemCount() = groupList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groupList[position]
        holder.groupName.text = item.groupName
        for(default in defaultList){
            if(item.groupName == default.groupName){
                holder.groupEdit.visibility = INVISIBLE
                holder.groupDelete.visibility = INVISIBLE
                holder.groupSort.visibility = INVISIBLE
            }
        }
    }
}