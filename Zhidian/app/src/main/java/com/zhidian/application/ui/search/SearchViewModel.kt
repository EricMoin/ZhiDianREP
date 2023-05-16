package com.zhidian.application.ui.search

import android.app.DownloadManager.Query
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.logic.model.SearchRepository

class SearchViewModel : ViewModel() {
    private val _historyLiveData = MutableLiveData< List<StockItem> >()
    val historyLiveData get() = _historyLiveData
    private val _rankLiveData = MutableLiveData< List<StockItem> >()
    val rankLiveData get() = _rankLiveData
    private val _searchLiveData = MutableLiveData< List<StockItem> >()
    val searchLiveData get() = _searchLiveData
    val historyList = ArrayList<StockItem>()
    val searchList = ArrayList<StockItem>()
    val rankList = ArrayList<StockItem>()
    fun getHistory(){
        _historyLiveData.value = SearchRepository.getHistory()
    }
    fun loadHistory() = SearchRepository.loadHistory()
    fun saveHistory() = SearchRepository.saveHistory(historyList)
    fun getRank(){
        _rankLiveData.value = SearchRepository.getRank()
    }
    fun getSearch(query:String){
        _searchLiveData.value = SearchRepository.getSearch(query)
    }
}