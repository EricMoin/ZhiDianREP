package com.zhidian.stockmanager.ui.stockmange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhidian.stockmanager.logic.GroupRepository
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem
class ManageStockViewModel:ViewModel() {
    private val _stocksLiveData = MutableLiveData<List<ManageStockItem>>()
    val stocksLiveData get() = _stocksLiveData
    lateinit var groupName:String
    val stocks = ArrayList<ManageStockItem>()
    fun getGroup(name:String){
        groupName = name
        val list = GroupRepository.getGroup(name)
        _stocksLiveData.value = list
    }
    fun saveGroup(group:ManageGroupItem) = GroupRepository.saveGroup(group)
    fun getGroupList() = GroupRepository.getGroupList()
    fun moveStockTo(stock:ManageStockItem, groupName:String) = GroupRepository.moveStockTo(stock,groupName)
    fun removeStockInAllGroups(stockName: String) = GroupRepository.removeStockInAllGroups(stockName)
}