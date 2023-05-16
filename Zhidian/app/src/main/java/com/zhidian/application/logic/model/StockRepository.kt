package com.zhidian.application.logic.model

object StockRepository {
    fun getStockList() = StockDao.getStockList()
}