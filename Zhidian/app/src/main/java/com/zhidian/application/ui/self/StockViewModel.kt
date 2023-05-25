package com.zhidian.application.ui.self

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.logic.model.StockRepository

class StockViewModel : ViewModel() {
    private val _stockLiveData = MutableLiveData< ArrayList<StockItem> >()
    val stockLiveData get() = _stockLiveData
    val stockList = ArrayList<StockItem>()
    fun loadData(path:String) = StockRepository.loadData(path)
    fun refreshStockList():ArrayList<StockItem>{
        val list = StockRepository.getStockList()
        stockList.clear()
        stockList.addAll(list)
        _stockLiveData.value = stockList
        return stockList
    }
}