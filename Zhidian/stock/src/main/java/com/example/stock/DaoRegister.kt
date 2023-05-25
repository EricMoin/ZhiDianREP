package com.example.stock

import android.content.Context
import com.example.analyst.model.AnalyzeChartDao
import com.example.analyst.model.TrackDao
import com.example.stock.model.SingleStockDao
import java.io.File

object DaoRegister {
    fun registerPath(context: Context){
        val path = context.filesDir.absolutePath+ File.separator
        SingleStockDao.setLocalPath(path)
        AnalyzeChartDao.setLocalPath(path)
        TrackDao.setLocalPath(path)
    }
}