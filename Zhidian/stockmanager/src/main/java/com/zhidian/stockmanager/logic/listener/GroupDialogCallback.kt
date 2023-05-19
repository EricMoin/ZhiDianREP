package com.zhidian.stockmanager.logic.listener

import com.zhidian.stockmanager.logic.data.ManageGroupItem

interface GroupDialogCallback {
    fun setSelectedGroups(list:List<ManageGroupItem>)
}