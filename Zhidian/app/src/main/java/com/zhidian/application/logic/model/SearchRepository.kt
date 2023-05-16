package com.zhidian.application.logic.model

import android.util.Log
import com.zhidian.application.logic.data.StockItem

object SearchRepository {
    fun loadHistory() = SearchDao.loadHistory()
    fun getHistory() = SearchDao.getHistory()
    fun saveHistory(list:List<StockItem>) = SearchDao.saveHistory(list)
    fun getRank() = RankDao.getRank()
    fun getSearch(query:String):List<StockItem>{
        val list = RankDao.getRank()
        val retList = ArrayList<StockItem>()
        for(stock in list){
            if(stock.name.contains(query)) retList.add(stock)
        }
        Log.d("SearchRepository","list size is ${retList.size}")
        return retList
    }
}