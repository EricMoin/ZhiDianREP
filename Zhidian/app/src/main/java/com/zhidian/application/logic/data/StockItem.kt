package com.zhidian.application.logic.data

data class StockItem(
    val name:String,
    val id:String,
    val icon:String,
    val label:String,
    val newestPrice:String,
    val planPrice:String,
    val range:String,
    var hasFocused:Boolean,
    val from:String
)
