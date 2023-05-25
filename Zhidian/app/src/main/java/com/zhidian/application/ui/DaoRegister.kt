package com.zhidian.application.ui

import android.content.Context
import com.zhidian.application.logic.model.StockDao
import com.zhidian.stockmanager.logic.dao.GroupDao
import java.io.File

object DaoRegister{
    fun registerPath(context:Context){
        val path = context.filesDir.absolutePath+File.separator
        StockDao.setLocalPath(path)
        GroupDao.setLocalPath(path)
    }
}