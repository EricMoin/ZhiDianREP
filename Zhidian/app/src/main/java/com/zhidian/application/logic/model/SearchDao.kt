package com.zhidian.application.logic.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.ui.ZhidianApplication
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import kotlin.random.Random

object SearchDao {
    private val historyList = ArrayList<StockItem>()
    private val path = ZhidianApplication.context.filesDir.absolutePath+ File.separator
    const val JSON  =".json"
    const val HISTORY = "[HISTORY]"
    init {
//        makeHistory()
    }
    private fun makeHistory() {
        historyList.add(
            StockItem(
                name = Random.nextInt().toString(),
                code = Random.nextInt().toString(),
                icon = " 创 ",
                label = "",
                newestPrice = Random.nextInt().toString(),
                planPrice = Random.nextInt().toString(),
                range = "-5.20%",
                hasFocused = false,
                from = "600519.SZ"
            )
        )
        historyList.add(
            StockItem(
                name = Random.nextInt().toString(),
                code = Random.nextInt().toString(),
                icon = " 创 ",
                label = "",
                newestPrice = Random.nextInt().toString(),
                planPrice = Random.nextInt().toString(),
                range = "+5.20%",
                hasFocused = true,
                from = "600519.SH"
            )
        )
    }
    fun saveHistory(stock:StockItem){
        val fw = FileWriter(path+HISTORY+"${stock.code}")
        GsonBuilder().setPrettyPrinting().create().toJson(stock,fw)
        fw.flush()
        fw.close()
        historyList.add(stock)
    }
    fun loadHistory(){
        historyList.clear()
        val dir = File(path)
        for(file in dir.listFiles()){
            if (file.name.contains(HISTORY)){
                val fr = FileReader(file)
                historyList.add(Gson().fromJson(fr,StockItem::class.java))
                fr.close()
            }
        }
    }
    fun removeHistory(stock: StockItem){
        for(position in historyList.indices){
            if (historyList[position].code == stock.code) historyList.removeAt(position)
        }
        val dir = File(path)
        for (file in dir.listFiles()){
            if(file.name.contains(stock.code)&&file.name.contains(HISTORY)){
                file.delete()
            }
        }
    }
    fun getHistory() = historyList
}