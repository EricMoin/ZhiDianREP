package com.zhidian.application.logic.model

object StockRepository {
    fun loadData(path:String) = StockDao.loadData(path)
    fun getStockList() = StockDao.getStockList()
}