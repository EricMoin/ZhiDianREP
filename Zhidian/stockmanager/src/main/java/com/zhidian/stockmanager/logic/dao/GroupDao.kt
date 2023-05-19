package com.zhidian.stockmanager.logic.dao

import android.util.Log
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem
import kotlin.random.Random

object GroupDao {
    private val groupList = ArrayList<ManageGroupItem>()
    private val defaultList = ArrayList<ManageGroupItem>()
    init {
        initDefaultList()
        makeGroup()
    }
    private fun initDefaultList() {
        defaultList.add( ManageGroupItem("全部",ArrayList<ManageStockItem>()) )
        defaultList.add( ManageGroupItem("沪深",ArrayList<ManageStockItem>()) )
        defaultList.add( ManageGroupItem("港股",ArrayList<ManageStockItem>()) )
        defaultList.add( ManageGroupItem("美股",ArrayList<ManageStockItem>()) )
    }
    fun randomStocks():List<ManageStockItem>{
        val stocks = ArrayList<ManageStockItem>()
        repeat(3){
            stocks.add(
                ManageStockItem(Random.nextInt().toString(),Random.nextInt().toString(),0,false)
            )
        }
        return stocks
    }
    fun makeGroup(){
        groupList.clear()
        for( item in defaultList){
            groupList.add( ManageGroupItem( item.groupName, randomStocks()) )
        }
    }
    fun getGroup(name:String): List<ManageStockItem> {
        val list = ArrayList<ManageStockItem>()
        for( group in groupList ){
            if(group.groupName == name){
                list.addAll(group.stocks)
                break
            }
        }
        Log.d("Dao","${name} 获取数据")
        return list
    }
    fun saveGroup(group:ManageGroupItem){
        for (item in groupList){
            if (item.groupName==group.groupName){
                item.stocks = group.stocks
            }
        }
    }
    fun updateGroups(groups:List<ManageGroupItem>){
        groupList.clear()
        groupList.addAll(groups)
    }
    fun getGroupList() = groupList
    fun getDefaultList() = defaultList
    fun removeStockInAllGroups(stockName:String){
        for(group in groupList){
            val size = group.stocks.size-1
            for(position in size downTo 0){
                val stock = group.stocks[position]
                if (stock.name == stockName){
                    val list = ArrayList<ManageStockItem>(group.stocks)
                    list.removeAt(position)
                    group.stocks = list
                }
            }
        }
    }
    fun moveStockTo(stock:ManageStockItem, groupName:String){
        val list = ArrayList<ManageStockItem>()
        for( group in groupList ){
            if(group.groupName == groupName){
                list.addAll(group.stocks)
                list.add(stock)
                group.stocks = list
            }
        }
    }
}