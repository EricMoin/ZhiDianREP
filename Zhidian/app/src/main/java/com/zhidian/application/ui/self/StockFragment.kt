package com.zhidian.application.ui.self

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhidian.application.R
import com.zhidian.application.ui.adapter.SelfStockAdapter

class StockFragment : Fragment() {

    companion object {
        fun newInstance() = StockFragment()
    }

    val viewModel by lazy { ViewModelProvider(this)[StockViewModel::class.java] }
    private lateinit var mainView:View
    private lateinit var adapter:SelfStockAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_stock, container, false)
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }
    private fun initRecyclerView() {
        val selfRecyclerView = mainView.findViewById<RecyclerView>(R.id.selfRecyclerView)
        val layoutManger = LinearLayoutManager(requireActivity())
        selfRecyclerView.layoutManager = layoutManger
        adapter = SelfStockAdapter(this,viewModel.stockList)
        selfRecyclerView.adapter = adapter
        viewModel.refreshStockList()
        adapter.notifyDataSetChanged()
    }
}