package com.zhidian.application.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhidian.application.logic.data.StockItem
import com.zhidian.application.logic.model.SearchRepository
import com.zhidian.application.logic.model.StockRepository
import com.zhidian.stockmanager.logic.GroupRepository
import com.zhidian.stockmanager.logic.data.ManageGroupItem
import com.zhidian.stockmanager.logic.data.ManageStockItem

class SearchViewModel : ViewModel() {
    private val _historyLiveData = MutableLiveData< List<StockItem> >()
    val historyLiveData get() = _historyLiveData
    private val _rankLiveData = MutableLiveData< List<StockItem> >()
    val rankLiveData get() = _rankLiveData
    private val _searchLiveData = MutableLiveData< List<StockItem> >()
    val searchLiveData get() = _searchLiveData
    val historyList = ArrayList<StockItem>()
    val searchList = ArrayList<StockItem>()
    val rankList = ArrayList<StockItem>()
    val groups = ArrayList<ManageGroupItem>()
    fun getHistory(){
        _historyLiveData.value = SearchRepository.getHistory()
    }
    fun loadHistory() = SearchRepository.loadHistory()
    fun saveHistory(stock:StockItem) = SearchRepository.saveHistory(stock)
    fun getRank(){
        _rankLiveData.value = SearchRepository.getRanks()
    }
    fun getSearch(query:String,uri:String){
        _searchLiveData.value = SearchRepository.getSearch(query,uri)
    }
    fun loadGroups() = GroupRepository.loadGroups()
    fun loadGroups(path:String) = GroupRepository.loadGroups(path)
    fun saveGroup(group: ManageGroupItem) = GroupRepository.saveGroup(group)
    fun getGroupList() = GroupRepository.getGroupList()
    fun moveStockTo(stock:ManageStockItem, groupName:String) = GroupRepository.moveStockTo(stock,groupName)
    fun removeStockInAllGroups(stockName: String) = GroupRepository.removeStockInAllGroups(stockName)
    fun newGroup(groupName:String) = groups.add(ManageGroupItem( groupName,ArrayList<ManageStockItem>() ) )
    fun updateGroups(groups:List<ManageGroupItem>) = GroupRepository.updateGroups(groups)
    fun getDefaultList() = GroupRepository.getDefaultList()
    fun deleteGroup(position:Int) = groups.removeAt(position)
    fun removeHistory(stock: StockItem){
        historyList.remove(stock)
        SearchRepository.removeHistory(stock)
    }
    fun queryHasFocused(item:StockItem):Boolean{
        loadGroups()
        val list = getGroupList()
        for(group in list){
            for(stock in group.stocks){
                Log.d("ViewModel","query ${item.code} ?: ${stock.id}")
                if(item.code == stock.id) return true
            }
        }
        return false
    }
}