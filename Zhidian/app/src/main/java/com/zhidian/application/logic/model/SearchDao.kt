package com.zhidian.application.logic.model

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
    init {
        makeHistory()
    }

    private fun makeHistory() {
        historyList.add(
            StockItem(
                name = Random.nextInt().toString(),
                id = Random.nextInt().toString(),
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
                id = Random.nextInt().toString(),
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
    fun saveHistory(list:List<StockItem>){
        val fw = FileWriter(path)
        GsonBuilder().setPrettyPrinting().create().toJson(list,fw)
        fw.flush()
        fw.close()
        historyList.clear()
        historyList.addAll(list)
    }
    fun loadHistory(){
        historyList.clear()
        val dir = File(path)
        for(file in dir.listFiles()){
            if (file.name.contains(".json")){
                val fr = FileReader(file)
                val reader = JsonReader(fr)
                historyList.addAll(GsonBuilder().create().fromJson< ArrayList<StockItem> >(reader,object :TypeToken< ArrayList<StockItem> >() {}.type))
                reader.close()
                fr.close()
            }
        }
    }
    fun getHistory() = historyList
}