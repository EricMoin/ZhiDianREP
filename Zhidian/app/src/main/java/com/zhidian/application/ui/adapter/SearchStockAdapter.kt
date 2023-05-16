package com.zhidian.application.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.stock.StockActivity
import com.zhidian.application.R
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.ui.dialog.GroupAddDialog
import com.zhidian.application.ui.dialog.GroupDialog

class SearchStockAdapter(val activity: FragmentActivity, val itemList:List<StockItem>): RecyclerView.Adapter<SearchStockAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stockName = view.findViewById<TextView>(R.id.stockName)
        val stockIcon = view.findViewById<TextView>(R.id.stockIcon)
        val stockId = view.findViewById<TextView>(R.id.stockId)
        val stockFocus = view.findViewById<ImageView>(R.id.stockFocus)
        val stockHasFocused = view.findViewById<TextView>(R.id.stockHasFocused)
        val stockDelHistory = view.findViewById<ImageView>(R.id.stockDelHistory)
    }
    val dialog = GroupDialog(activity, com.google.android.material.R.style.Theme_Material3_DayNight_Dialog)
    val addDialog = GroupAddDialog(activity, com.google.android.material.R.style.Theme_Material3_DayNight_Dialog)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val intent = Intent(activity,StockActivity::class.java)
            activity.startActivity(intent)
        }
        return holder
    }
    override fun getItemCount() = itemList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.stockName.text = item.name
        holder.stockIcon.text = item.icon
        holder.stockId.text = item.id
        holder.stockFocus.visibility = if (item.hasFocused) GONE else VISIBLE
        holder.stockFocus.setOnClickListener {
            dialog.show()
        }
        holder.stockHasFocused.visibility = if (item.hasFocused) VISIBLE else GONE
    }
}