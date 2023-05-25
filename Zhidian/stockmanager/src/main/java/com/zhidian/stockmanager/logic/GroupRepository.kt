package com.zhidian.stockmanager.logic

import com.zhidian.stockmanager.logic.dao.GroupDao
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem

object GroupRepository {
    fun getGroup(name:String) = GroupDao.getGroup(name)
    fun setLocalPath(str:String)  = GroupDao.setLocalPath(str)
    fun loadGroups() = GroupDao.loadGroups()
    fun loadGroups(path:String) = GroupDao.loadGroups(path)
    fun updateGroup(position:Int,group:ManageGroupItem) = GroupDao.updateGroup(position,group)
    fun removeGroup(position:Int)  = GroupDao.removeGroup(position)
    fun writeGroups() = GroupDao.writeGroups()
    fun updateGroups(groups:List<ManageGroupItem>) = GroupDao.updateGroups(groups)
    fun getGroupList() = GroupDao.getGroupList()
    fun getDefaultList() = GroupDao.getDefaultList()
    fun removeStockInAllGroups(stockName:String) = GroupDao.removeStockInAllGroups(stockName)
    fun saveGroup(group:ManageGroupItem) = GroupDao.saveGroup(group)
    fun moveStockTo(stock: ManageStockItem, groupName:String) = GroupDao.moveStockTo(stock,groupName)
}