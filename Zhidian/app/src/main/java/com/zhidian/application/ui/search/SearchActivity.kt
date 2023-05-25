package com.zhidian.application.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhidian.application.R
import com.zhidian.application.ui.adapter.SearchStockAdapter
import com.zhidian.application.ui.adapter.RankStockAdapter
import java.io.File

class SearchActivity : AppCompatActivity() {
    val viewModel by lazy{ ViewModelProvider(this)[SearchViewModel::class.java] }
    private lateinit var historyAdapter: SearchStockAdapter
    private lateinit var rankAdapter:RankStockAdapter
    private lateinit var searchAdapter:SearchStockAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initRank()
        initHistory()
        initHistoryPanel()
        initSearchBox()
    }
    private fun initSearchBox() {
        val searchBox = findViewById<EditText>(R.id.searchBox)
        val searchRankContainer = findViewById<LinearLayout>(R.id.searchRankContainer)
        val searchHistoryContainer = findViewById<LinearLayout>(R.id.searchHistoryContainer)
        val searchRecycler = findViewById<RecyclerView>(R.id.searchRecycler)
        searchRecycler.layoutManager = LinearLayoutManager(this)
        searchAdapter = SearchStockAdapter(this,viewModel.searchList)
        searchRecycler.adapter = searchAdapter
        searchBox.addTextChangedListener{
            if(it.toString().isNotEmpty()){
                searchRankContainer.visibility = GONE
                searchHistoryContainer.visibility = GONE
                searchRecycler.visibility = VISIBLE
                viewModel.getSearch(it.toString(),filesDir.absolutePath+File.separator)
            }else{
                searchRankContainer.visibility = VISIBLE
                searchHistoryContainer.visibility = VISIBLE
                searchRecycler.visibility = GONE
            }
        }
        viewModel.searchLiveData.observe(
            this, Observer {
                result ->
                if(result.isNotEmpty()){
                    viewModel.searchList.clear()
                    viewModel.searchList.addAll(result)
                    Log.d("SearchActivity","Receive size is ${viewModel.searchList.size}")
                    searchAdapter.notifyDataSetChanged()
                }
            }
        )
        val searchBegin = findViewById<TextView>(R.id.searchBegin)
    }
    private fun initHistoryPanel() {
        val searchDelHistoryBtn = findViewById<ImageView>(R.id.searchDelHistoryBtn)
        val stockDelAll = findViewById<TextView>(R.id.stockDelAll)
        val stockFinish = findViewById<TextView>(R.id.stockFinish)
        val searchRecycler = findViewById<RecyclerView>(R.id.searchRecycler)
        searchDelHistoryBtn.setOnClickListener {
            stockDelAll.visibility = VISIBLE
            stockFinish.visibility = VISIBLE
            searchDelHistoryBtn.visibility = GONE
            historyAdapter.deletable = true
            historyAdapter.notifyDataSetChanged()
        }
        stockFinish.setOnClickListener {
            stockDelAll.visibility = GONE
            stockFinish.visibility = GONE
            searchRecycler.visibility = GONE
            searchDelHistoryBtn.visibility = VISIBLE
            historyAdapter.deletable = false
            historyAdapter.notifyDataSetChanged()
        }
        stockDelAll.setOnClickListener {
            for(stock in viewModel.historyList){
                viewModel.removeHistory(stock)
            }
            viewModel.historyList.clear()
            historyAdapter.notifyDataSetChanged()
        }
    }
    private fun initHistory() {
        val searchHistoryRecycler = findViewById<RecyclerView>(R.id.searchHistoryRecycler)
        val layoutManager = LinearLayoutManager(this)
        searchHistoryRecycler.layoutManager = layoutManager
        historyAdapter = SearchStockAdapter(this,viewModel.historyList)
        searchHistoryRecycler.adapter = historyAdapter
        viewModel.loadHistory()
        viewModel.getHistory()
        viewModel.historyLiveData.observe(
            this, Observer {
                result ->
                if(result.isNotEmpty()){
                    viewModel.historyList.clear()
                    viewModel.historyList.addAll(result)
                    historyAdapter.notifyDataSetChanged()
                }
            }
        )
    }
    private fun initRank() {
        val searchRankRecycler = findViewById<RecyclerView>(R.id.searchRankRecycler)
        val layoutManager = GridLayoutManager(this,3)
        searchRankRecycler.layoutManager = layoutManager
        rankAdapter = RankStockAdapter(this,viewModel.rankList)
        searchRankRecycler.adapter = rankAdapter
        viewModel.getRank()
        viewModel.rankLiveData.observe(
            this, Observer {
                result ->
                if(result.isNotEmpty()){
                    viewModel.rankList.clear()
                    viewModel.rankList.addAll(result)
                    rankAdapter.notifyDataSetChanged()
                }
            }
        )
    }
}