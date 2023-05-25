package com.zhidian.application.ui.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.stock.StockActivity
import com.zhidian.application.R
import com.zhidian.application.logic.data.StockItem
import kotlin.random.Random

class SelfStockAdapter(val fragment: Fragment, val itemList:List<StockItem>): RecyclerView.Adapter<SelfStockAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stockName = view.findViewById<TextView>(R.id.stockName)
        val stockIcon = view.findViewById<TextView>(R.id.stockIcon)
        val stockId = view.findViewById<TextView>(R.id.stockId)
        val stockNewestPrice = view.findViewById<TextView>(R.id.stockNewestPrice)
        val stockRange = view.findViewById<TextView>(R.id.stockRange)
        val stockLabel = view.findViewById<TextView>(R.id.stockLabel)
        val stockPlanText = view.findViewById<TextView>(R.id.stockPlanText)
        val stockPlanData = view.findViewById<TextView>(R.id.stockPlanData)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = itemList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.stockName.text = item.name
        holder.stockId.text = item.code
        holder.stockIcon.text = item.icon
        holder.stockNewestPrice.text = item.newestPrice
        holder.stockRange.text = if(item.range.contains("-")) "${item.range}%" else "+${item.range}%"
        val down = ColorStateList.valueOf(fragment.resources.getColor(R.color.data_green))
        val up = ColorStateList.valueOf(fragment.resources.getColor(R.color.data_red))
        val color = if(item.range.contains("-")) down else up
        holder.stockRange.backgroundTintList = color
        holder.stockNewestPrice.setTextColor(color)
        holder.itemView.setOnClickListener {
            val intent = Intent(fragment.activity,StockActivity::class.java)
            intent.putExtra(StockActivity.CODE,item.code)
            fragment.startActivity(intent)
        }
        holder.stockPlanData.text = String.format("%.2f",item.newestPrice.toDouble() - Random.nextDouble()%item.newestPrice.toDouble())
    }
}