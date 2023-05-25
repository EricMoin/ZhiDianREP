package com.zhidian.stockmanager.ui.groupmanage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhidian.stockmanager.logic.GroupRepository
import com.zhidian.stockmanager.logic.dao.GroupDao
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem

class ManageGroupViewModel : ViewModel() {
    private val _groupLiveData = MutableLiveData< List<ManageGroupItem> >()
    val groupLiveData get() = _groupLiveData
    val groups = ArrayList<ManageGroupItem>()
    fun getGroupList(){
        _groupLiveData.value = GroupRepository.getGroupList()
    }
    fun setLocalPath(path:String) = GroupDao.setLocalPath(path)
    fun newGroup(groupName:String) = groups.add(ManageGroupItem( groupName,ArrayList<ManageStockItem>() ) )
    fun updateGroup(position:Int,group:ManageGroupItem) = GroupDao.updateGroup(position,group)
    fun updateGroups(groups:List<ManageGroupItem>) = GroupRepository.updateGroups(groups)
    fun loadGroups() = GroupRepository.loadGroups()
    fun writeGroups() = GroupRepository.writeGroups()
    fun getDefaultList() = GroupRepository.getDefaultList()
    fun removeGroup(position:Int) = GroupRepository.removeGroup(position)
}