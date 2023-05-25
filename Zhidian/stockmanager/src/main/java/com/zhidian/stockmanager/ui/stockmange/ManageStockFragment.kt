package com.zhidian.stockmanager.ui.stockmange

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.zhidian.stockmanager.R
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.listener.GroupDialogCallback
import com.zhidian.stockmanager.logic.utils.StockMoveHelper
import com.zhidian.stockmanager.ui.adapter.ManageStockAdapter
import com.zhidian.stockmanager.ui.dialog.GroupDialog
import java.io.File
import java.util.Collections

class ManageStockFragment() : Fragment() {

    companion object {
        fun newInstance() = ManageStockFragment()
    }
    private lateinit var mainView:View
    private lateinit var adapter:ManageStockAdapter
    val viewModel by lazy{ ViewModelProvider(this)[ManageStockViewModel::class.java] }
    private lateinit var groupName:String
    private lateinit var path:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        groupName = arguments?.getString("group_name")?:""
        mainView = inflater.inflate(R.layout.fragment_manage_stock, container, false)
        return mainView
    }
    override fun onResume() {
        super.onResume()
        viewModel.getGroup(groupName)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        path = activity?.filesDir?.absolutePath+ File.separator
        initStockList()
        initBottomMenu()

    }
    private fun initBottomMenu() {
        val stockSelectAll = mainView.findViewById<MaterialCheckBox>(R.id.stockSelectAll)
        stockSelectAll.setOnCheckedChangeListener { compoundButton, b ->
            adapter.isSelectAll = b
            adapter.notifyItemRangeChanged(0,viewModel.stocks.size)
        }
        val stockRemove = mainView.findViewById<TextView>(R.id.stockRemove)
        stockRemove.setOnClickListener {
            adapter.isSelectAll = false
            val size = viewModel.stocks.size-1
            for( position in size downTo  0 ){
                val item = viewModel.stocks[position]
                if(item.isSelected){
                    viewModel.stocks.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    viewModel.saveGroup(ManageGroupItem(groupName,viewModel.stocks))
                    viewModel.writeGroups()
                }
            }
        }
        val stockDelete = mainView.findViewById<TextView>(R.id.stockDelete)
        stockDelete.setOnClickListener {
            val size = viewModel.stocks.size-1
            for( position in size downTo  0 ){
                val item = viewModel.stocks[position]
                if(item.isSelected){
                    viewModel.removeStockInAllGroups(item.name)
                    viewModel.getGroup(groupName)
                }
            }
        }
        val stockMove = mainView.findViewById<TextView>(R.id.stockMove)
        stockMove.setOnClickListener {
            val dialog = GroupDialog(requireActivity(), com.google.android.material.R.style.Theme_Material3_DayNight_Dialog)
            dialog.show()
            dialog.loadGroups(viewModel.getGroupList())
            dialog.ignoreGroup(groupName)
            dialog.setCallback(
                object :GroupDialogCallback{
                    override fun setSelectedGroups(list: List<ManageGroupItem>) {
                        if(list.isEmpty()) return
                        val size = viewModel.stocks.size-1
                        for( position in size downTo  0 ){
                            val item = viewModel.stocks[position]
                            if(item.isSelected){
                                for (group in list){
                                    viewModel.moveStockTo(item,group.groupName)
                                }
                                viewModel.stocks.removeAt(position)
                                adapter.notifyItemRemoved(position)
                            }
                        }
                        viewModel.saveGroup(ManageGroupItem(groupName,viewModel.stocks))
                    }
                }
            )
        }
    }
    private fun initStockList() {
        val stockManageRecycler = mainView.findViewById<RecyclerView>(R.id.stockManageRecycler)
        val layoutManager = LinearLayoutManager(activity)
        stockManageRecycler.layoutManager = layoutManager
        val callback = StockMoveHelper(
                object : StockMoveHelper.MyOnItemTouchListener{
                    override fun onMove(fromPosition: Int, toPosition: Int) {
                        adapter.notifyItemMoved(fromPosition,toPosition)
                        Collections.swap(viewModel.stocks,fromPosition,toPosition)
                        viewModel.saveGroup(ManageGroupItem(groupName,viewModel.stocks))
                    }
                    override fun onSwiped(position: Int) {

                    }
                    override fun onSelectedChanged(
                        viewHolder: RecyclerView.ViewHolder?,
                        actionState: Int
                    ) {

                    }
                    override fun onClear(viewHolder: RecyclerView.ViewHolder) {

                    }
                }
        )
        adapter = ManageStockAdapter(this,callback,viewModel.stocks)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(stockManageRecycler)
        stockManageRecycler.adapter = adapter
        viewModel.stocksLiveData.observe(
            viewLifecycleOwner, Observer {
                result ->
                viewModel.stocks.clear()
                viewModel.stocks.addAll(result)
                adapter.notifyDataSetChanged()
            }
        )
        viewModel.getGroup(groupName)
    }
}