package com.zhidian.application.logic.model

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.ui.ZhidianApplication
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object RankDao {
    private val rankList = ArrayList<StockItem>()
    private val path = ZhidianApplication.context.filesDir.absolutePath+ File.separator
    init{
        repeat(3){
            makeRank()
        }
    }
    private fun makeRank() {
        rankList.add(
            StockItem(
                name = "长安汽车",
                id = "300274",
                icon = " 创 ",
                label = "",
                newestPrice = "306.80",
                planPrice = "306.80",
                range = "-5.20%",
                hasFocused = true,
                from = "600519.SZ"
            )
        )
        rankList.add(
            StockItem(
                name = "阳光电源",
                id = "300274",
                icon = " 创 ",
                label = "",
                newestPrice = "999.80",
                planPrice = "306.80",
                range = "+5.20%",
                hasFocused = false,
                from = "600519.SH"
            )
        )
        rankList.add(
            StockItem(
                name = "伊利股份",
                id = "300274",
                icon = " 创 ",
                label = "",
                newestPrice = "1200.80",
                planPrice = "306.80",
                range = "+5.60%",
                hasFocused = true,
                from = "600519.SZ"
            )
        )
    }
    fun getRank() = rankList
}