package com.example.analyst.model

import com.example.analyst.network.TrendResponse

object AnalyzeChartRepository {
    fun loadDataFromLocal(code:String) = AnalyzeChartDao.loadDataFromLocal(code)
}