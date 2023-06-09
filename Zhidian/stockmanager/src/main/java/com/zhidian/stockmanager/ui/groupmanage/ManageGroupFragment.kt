package com.zhidian.stockmanager.ui.groupmanage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.zhidian.stockmanager.R
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.listener.GroupListener
import com.zhidian.stockmanager.logic.utils.StockMoveHelper
import com.zhidian.stockmanager.ui.adapter.ManageGroupAdapter
import com.zhidian.stockmanager.ui.adapter.ManageStockAdapter
import com.zhidian.stockmanager.ui.dialog.GroupAddDialog
import java.io.File
import java.util.Collections
import kotlin.concurrent.thread

class ManageGroupFragment : Fragment() {

    companion object {
        fun newInstance() = ManageGroupFragment()
    }
    private lateinit var mainView:View
    private lateinit var adapter:ManageGroupAdapter
    lateinit var dialog: GroupAddDialog
    val viewModel by lazy { ViewModelProvider(this)[ManageGroupViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_manage_group, container, false)
        return mainView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGroupList()
        initAddButton()
    }
    private fun initAddButton() {
        val groupAddContainer = mainView.findViewById<LinearLayout>(R.id.groupAddContainer)
        dialog = GroupAddDialog(requireActivity(),com.google.android.material.R.style.Theme_Material3_DayNight_Dialog)
        groupAddContainer.setOnClickListener {
            dialog.setGroupListener(
                object :GroupListener{
                    override fun setGroup(groupName: String) {
                        viewModel.newGroup(groupName)
                        viewModel.updateGroups(viewModel.groups)
                        viewModel.writeGroups()
                        viewModel.getGroupList()
                    }
                }
            )
            dialog.show()
        }
    }
    private fun initGroupList() {
        val groupManageRecycler = mainView.findViewById<RecyclerView>(R.id.groupManageRecycler)
        val layoutManager = LinearLayoutManager(activity)
        groupManageRecycler.layoutManager = layoutManager
        viewModel.getGroupList()
        viewModel.groupLiveData.observe(
            viewLifecycleOwner, Observer {
                result ->
                if(result.isNotEmpty()){
                    viewModel.groups.clear()
                    viewModel.groups.addAll(result)
                    adapter.notifyDataSetChanged()
                    Log.d("Fragment","result size is ${result.size}")
                }
            }
        )
        val callback = StockMoveHelper(
            object : StockMoveHelper.MyOnItemTouchListener{
                override fun onMove(fromPosition: Int, toPosition: Int) {
                    adapter.notifyItemMoved(fromPosition,toPosition)
                    Collections.swap(viewModel.groups,fromPosition,toPosition)
                    viewModel.updateGroups(viewModel.groups)
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
        adapter = ManageGroupAdapter(this,callback,viewModel.groups)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(groupManageRecycler)
        groupManageRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}