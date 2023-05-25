package com.zhidian.stockmanager.ui.stockmange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhidian.stockmanager.logic.GroupRepository
import com.zhidian.stockmanager.logic.dao.GroupDao
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem
class ManageStockViewModel:ViewModel() {
    private val _stocksLiveData = MutableLiveData<List<ManageStockItem>>()
    val stocksLiveData get() = _stocksLiveData
    lateinit var groupName:String
    val stocks = ArrayList<ManageStockItem>()
    fun getGroup(name:String){
        groupName = name
        _stocksLiveData.value = GroupRepository.getGroup(name)
    }
    fun setLocalPath(path:String) = GroupDao.setLocalPath(path)
    fun loadGroups() = GroupRepository.loadGroups()
    fun saveGroup(group:ManageGroupItem) = GroupRepository.saveGroup(group)
    fun writeGroups() = GroupRepository.writeGroups()
    fun getGroupList() = GroupRepository.getGroupList()
    fun moveStockTo(stock:ManageStockItem, groupName:String) = GroupRepository.moveStockTo(stock,groupName)
    fun removeStockInAllGroups(stockName: String) = GroupRepository.removeStockInAllGroups(stockName)
}