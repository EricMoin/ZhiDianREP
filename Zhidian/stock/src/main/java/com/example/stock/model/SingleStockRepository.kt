package com.example.stock.model

object SingleStockRepository {
    fun getStock(code:String) = SingleStockDao.getStock(code)
}