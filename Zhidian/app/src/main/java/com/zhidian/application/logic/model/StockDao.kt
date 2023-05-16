package com.zhidian.application.logic.model

import com.zhidian.application.logic.data.StockItem

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
                    id = "300274",
                    icon = " 创 ",
                    label = "",
                    newestPrice = "306.80",
                    planPrice = "306.80",
                    range = "-5.20%",
                    hasFocused = true,
                    from = "600298.SH"
                )
            )
            stockList.add(
                StockItem(
                    name = "阳光电源",
                    id = "300274",
                    icon = " 创 ",
                    label = "",
                    newestPrice = "122.80",
                    planPrice = "108.80",
                    range = "+6.60%",
                    hasFocused = true,
                    from = "659298.SZ"
                )
            )
        }
    }
}