package com.zhidian.application.logic.model

import com.google.gson.Gson
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.logic.network.ChartResponse
import java.io.File
import java.io.FileReader

object StockDao {
    private const val JSON = ".json"
    private const val STOCK = "[STOCK]"
    private val stockList = ArrayList<StockItem>()
    private var path = ""
    init {
//        makeData()
    }
    fun setLocalPath(str:String){
        path = str
    }
    fun getStockList() = stockList
    fun loadData(path:String){
        stockList.clear()
        val dir = File(path)
        val list = ArrayList<ChartResponse>()
        for(file in dir.listFiles()){
            if(file.name.contains(STOCK)){
                val fr = FileReader(file)
                val result = Gson().fromJson(fr,ChartResponse::class.java)
                val quote = result.chase.data.quote
                stockList.add(
                    StockItem(
                        name = quote.name,
                        code = quote.code,
                        icon = result.chase.data.tags[0].description,
                        label = quote.exchange,
                        newestPrice = quote.current.toString(),
                        planPrice = "未计划",
                        range = quote.chg.toString(),
                        hasFocused = false,
                        from = quote.symbol
                    )
                )
                fr.close()
            }
        }
    }
}