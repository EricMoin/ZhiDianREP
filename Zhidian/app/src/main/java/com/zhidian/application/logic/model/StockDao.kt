package com.zhidian.application.logic.model

import com.zhidian.application.logic.data.StockItem
import kotlin.random.Random

object StockDao {
    private val stockList = ArrayList<StockItem>()
    init {
        makeData()
    }
    fun getStockList() = stockList
    fun loadData(){

    }
    fun makeData(){
        repeat(10) {
            stockList.add(
                StockItem(
                    name = "阳光电源",
                    id = "600514",
                    icon = " 创 ",
                    label = "",
                    newestPrice = "306.80",
                    planPrice = "123.4",
                    range = "-5.20%",
                    hasFocused = false,
                    from = "600519.SZ"
                )
            )
            stockList.add(
                StockItem(
                    name = "长安汽车",
                    id = "600519",
                    icon = " 创 ",
                    label = "",
                    newestPrice = "270.94",
                    planPrice = "274.5",
                    range = "+5.20%",
                    hasFocused = false,
                    from = "600519.SH"
                )
            )
        }
    }
}