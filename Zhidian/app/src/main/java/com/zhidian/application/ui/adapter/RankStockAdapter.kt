package com.zhidian.application.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.zhidian.application.R
import com.zhidian.application.logic.data.StockItem

class RankStockAdapter(val activity: FragmentActivity, val itemList:List<StockItem>): RecyclerView.Adapter<RankStockAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stockRankItemName = view.findViewById<TextView>(R.id.stockRankItemName)
        val stockRankItemData = view.findViewById<TextView>(R.id.stockRankItemData)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rank_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }
    override fun getItemCount() = itemList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.stockRankItemName.text = item.name
        holder.stockRankItemData.text = item.from
        var color =
        if(item.from.contains("SH")) Color.parseColor("#21A538")
        else Color.parseColor("#E24141")
        holder.stockRankItemData.setTextColor(color)
    }
}