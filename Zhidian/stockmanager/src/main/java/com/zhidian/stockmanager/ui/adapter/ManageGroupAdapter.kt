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
import com.zhidian.stockmanager.ui.groupmanage.ManageGroupFragment

class ManageGroupAdapter(private val fragment:ManageGroupFragment,private val groupList:List<ManageGroupItem>): RecyclerView.Adapter<ManageGroupAdapter.ViewHolder>(){
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
            Log.d("Adapter","position is $position")
            fragment.viewModel.deleteGroup(position)
            notifyItemRemoved(position)
            fragment.viewModel.updateGroups(fragment.viewModel.groups)
        }
        holder.groupEdit.setOnClickListener {
            val position = holder.adapterPosition
            Log.d("Adapter","position is $position")
            fragment.dialog.setGroupListener(
                object : GroupListener {
                    override fun setGroup(groupName: String) {
                        fragment.viewModel.groups[position].groupName = groupName
                        notifyItemChanged(position)
                        fragment.viewModel.updateGroups(fragment.viewModel.groups)
                    }
                }
            )
            fragment.dialog.show()
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
            }
        }
    }
}