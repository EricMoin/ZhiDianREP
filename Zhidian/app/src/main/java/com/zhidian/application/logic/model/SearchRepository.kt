package com.zhidian.application.logic.model

import android.util.Log
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.logic.utils.Tools

object SearchRepository {
    fun loadHistory() = SearchDao.loadHistory()
    fun getHistory() = SearchDao.getHistory()
    fun saveHistory(stock: StockItem) = SearchDao.saveHistory(stock)
    fun removeHistory(stock: StockItem) = SearchDao.removeHistory(stock)
    fun getRanks():List<StockItem>{
        val list = StockDao.getStockList()
        Tools.sortByPrice(list,0)
        return list
    }
    fun getSearch(query:String,uri:String):List<StockItem>{
        StockDao.setLocalPath(uri)
        val list = StockDao.getStockList()
        val retList = ArrayList<StockItem>()
        for(stock in list){
            if(stock.name.contains(query)) retList.add(stock)
        }
        Log.d("SearchRepository","list size is ${retList.size}")
        return retList
    }
}