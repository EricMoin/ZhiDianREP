package com.zhidian.stockmanager.ui.groupmanage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhidian.stockmanager.logic.GroupRepository
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem

class ManageGroupViewModel : ViewModel() {
    private val _groupLiveData = MutableLiveData< List<ManageGroupItem> >()
    val groupLiveData get() = _groupLiveData
    val groups = ArrayList<ManageGroupItem>()
    fun getGroupList(){
        val list = GroupRepository.getGroupList()
        _groupLiveData.value = list
    }
    fun newGroup(groupName:String) = groups.add(ManageGroupItem( groupName,ArrayList<ManageStockItem>() ) )
    fun updateGroups(groups:List<ManageGroupItem>) = GroupRepository.updateGroups(groups)
    fun getDefaultList() = GroupRepository.getDefaultList()
    fun deleteGroup(position:Int) = groups.removeAt(position)
}