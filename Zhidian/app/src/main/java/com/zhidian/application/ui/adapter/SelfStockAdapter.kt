package com.zhidian.application.ui.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.stock.StockActivity
import com.zhidian.application.R
import com.zhidian.application.logic.data.StockItem

class SelfStockAdapter(val fragment: Fragment, val itemList:List<StockItem>): RecyclerView.Adapter<SelfStockAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stockName = view.findViewById<TextView>(R.id.stockName)
        val stockIcon = view.findViewById<TextView>(R.id.stockIcon)
        val stockId = view.findViewById<TextView>(R.id.stockId)
        val stockNewestPrice = view.findViewById<TextView>(R.id.stockNewestPrice)
        val stockRange = view.findViewById<TextView>(R.id.stockRange)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val intent = Intent(fragment.activity,StockActivity::class.java)
            fragment.startActivity(intent)
        }
        return holder
    }
    override fun getItemCount() = itemList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.stockName.text = item.name
        holder.stockId.text = item.id
        holder.stockIcon.text = item.icon
        holder.stockNewestPrice.text = item.newestPrice
        holder.stockRange.text = item.range
        val down = ColorStateList.valueOf(fragment.resources.getColor(R.color.data_green))
        val up = ColorStateList.valueOf(fragment.resources.getColor(R.color.data_red))
        val color = if(item.range.contains("-")) down else up
        holder.stockRange.backgroundTintList = color
        holder.stockNewestPrice.setTextColor(color)
    }
}