package com.zhidian.application.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stock.StockActivity
import com.zhidian.application.R
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.ui.search.SearchActivity
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem
import com.zhidian.stockmanager.logic.listener.GroupDialogCallback
import com.zhidian.stockmanager.ui.dialog.GroupDialog
import java.io.File

class SearchStockAdapter(val activity: SearchActivity, val itemList:List<StockItem>): RecyclerView.Adapter<SearchStockAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stockName = view.findViewById<TextView>(R.id.stockName)
        val stockIcon = view.findViewById<TextView>(R.id.stockIcon)
        val stockId = view.findViewById<TextView>(R.id.stockId)
        val stockFocus = view.findViewById<ImageView>(R.id.stockFocus)
        val stockHasFocused = view.findViewById<TextView>(R.id.stockHasFocused)
        val stockDelHistory = view.findViewById<ImageView>(R.id.stockDelHistory)
    }
    var deletable = false
    val dialog = GroupDialog(activity, com.google.android.material.R.style.Theme_Material3_DayNight_Dialog)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = itemList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.stockName.text = item.name
        holder.stockIcon.text = item.icon
        holder.stockId.text = item.code
        holder.stockFocus.setOnClickListener {
            dialog.show()
            activity.viewModel.loadGroups(activity.filesDir.absolutePath+File.separator)
            dialog.loadGroups(activity.viewModel.getGroupList())
            dialog.setCallback(
                object : GroupDialogCallback {
                    override fun setSelectedGroups(list: List<ManageGroupItem>) {
                        if (list.isEmpty()) return
                        for(group in list){
                            val stock = ManageStockItem(item.name,item.code,0,false)
                            activity.viewModel.moveStockTo(stock,group.groupName)
                        }
                    }
                }
            )
            item.hasFocused = true
            holder.stockHasFocused.visibility = VISIBLE
            holder.stockFocus.visibility = GONE
        }
        holder.stockHasFocused.setOnClickListener { true }
        holder.itemView.setOnClickListener {
            val intent = Intent(activity,StockActivity::class.java)
            intent.putExtra(StockActivity.CODE,item.code)
            activity.viewModel.saveHistory(item)
            activity.viewModel.loadHistory()
            activity.viewModel.getHistory()
            activity.startActivity(intent)
        }
        item.hasFocused = activity.viewModel.queryHasFocused(item)
        holder.stockFocus.visibility = if (item.hasFocused) GONE else VISIBLE
        holder.stockHasFocused.visibility = if (item.hasFocused) VISIBLE else GONE
        holder.stockFocus.visibility = if (deletable) GONE else holder.stockFocus.visibility
        holder.stockHasFocused.visibility = if (deletable) GONE else holder.stockHasFocused.visibility
        holder.stockDelHistory.visibility = if(deletable) VISIBLE else GONE
        holder.stockDelHistory.setOnClickListener {
            activity.viewModel.removeHistory(item)
            notifyItemRemoved(position)
            Log.d("Adapter","delete ${item.name}")
        }
    }
}