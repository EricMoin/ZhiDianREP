package com.zhidian.stockmanager.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.zhidian.stockmanager.R
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem
import com.zhidian.stockmanager.ui.stockmange.ManageStockFragment
import java.util.Collections

class ManageStockAdapter(private val fragment: ManageStockFragment, private val stockList:List<ManageStockItem>):RecyclerView.Adapter<ManageStockAdapter.ViewHolder>(){
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val stockName = view.findViewById<TextView>(R.id.stockName)
        val stockId = view.findViewById<TextView>(R.id.stockId)
        val stockCheckBox = view.findViewById<MaterialCheckBox>(R.id.stockCheckBox)
        val stockToTop = view.findViewById<ImageView>(R.id.stockToTop)
    }
    var isSelectAll = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = fragment.layoutInflater.inflate(R.layout.manage_stock_item,parent,false)
        val holder = ViewHolder(view)
        return holder
    }
    override fun getItemCount() = stockList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stockList[position]
        holder.stockName.text = item.name
        holder.stockId.text = item.id
        holder.stockCheckBox.isChecked = isSelectAll
        holder.stockCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            stockList[position].isSelected = b
        }
        holder.stockToTop.setOnClickListener {
            Collections.swap(fragment.viewModel.stocks,position,0)
            notifyItemMoved(position,0)
            fragment.viewModel.saveGroup(ManageGroupItem( fragment.viewModel.groupName,fragment.viewModel.stocks))
        }
    }
}