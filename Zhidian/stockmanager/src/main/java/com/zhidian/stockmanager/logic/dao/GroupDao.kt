package com.zhidian.stockmanager.logic.dao

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.stream.Collector
import java.util.stream.Collectors
import kotlin.random.Random

object GroupDao {
    private const val GROUP = "[GROUP]"
    private const val JSON = ".json"
    private val groupList = ArrayList<ManageGroupItem>()
    private val defaultList = ArrayList<ManageGroupItem>()
    private var path = ""
    init {
        initDefaultList()
    }
    fun setLocalPath(str:String){
        path = str
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
    fun loadGroups(){
        groupList.clear()
        val dir = File(path)
        if(!dir.exists()) return
        for(file in dir.listFiles()){
            if(file.name.contains(GROUP)){
                val fr = FileReader(file)
                val group = Gson().fromJson(fr,ManageGroupItem::class.java)
                groupList.add(group)
                fr.close()
            }
        }
        if(groupList.isEmpty()) groupList.addAll(defaultList)
    }
    fun loadGroups(path:String){
        groupList.clear()
        val dir = File(path)
        if(!dir.exists()) return
        for(file in dir.listFiles()){
            if(file.name.contains(GROUP)){
                val fr = FileReader(file)
                val group = Gson().fromJson(fr,ManageGroupItem::class.java)
                groupList.add(group)
                fr.close()
            }
        }
        if(groupList.isEmpty()) groupList.addAll(defaultList)
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
        writeGroups()
    }
    fun removeGroup(position: Int){
        groupList.removeAt(position)
    }
    fun updateGroup(position:Int,group:ManageGroupItem){
        groupList[position] = group
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
        writeGroups()
    }
    fun moveStockTo(stock:ManageStockItem, groupName:String){
        val list = ArrayList<ManageStockItem>()
        var exist = false
        for( group in groupList ){
            if(group.groupName == groupName){
                list.addAll(group.stocks)
                list.add(stock)
                group.stocks = list
                exist = true
            }
        }
        if(!exist){
            list.add(stock)
            val group = ManageGroupItem(groupName,list)
            groupList.add(group)
        }
        writeGroups()
    }
    fun writeGroups(){
        val dir = File(path)
        for (file in dir.listFiles()){
            if(file.name.contains(GROUP)) file.delete()
        }
        val list = ArrayList<ManageStockItem>()
        for(group in groupList){
            val fr = FileWriter(File(getFileName(group)))
            GsonBuilder().setPrettyPrinting().create().toJson(group,fr)
            fr.close()
            list.addAll(group.stocks)
        }
        groupList[0].stocks = list.stream().distinct().collect(Collectors.toList())
    }
    private fun getFileName(group: ManageGroupItem):String{
        val name = StringBuilder()
            .append(path)
            .append(GROUP)
            .append(group.groupName)
            .append(JSON)
            .toString()
        return name
    }
}