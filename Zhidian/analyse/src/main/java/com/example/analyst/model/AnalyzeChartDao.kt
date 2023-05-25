package com.example.analyst.model

import android.util.JsonReader
import android.util.Log
import com.example.analyst.network.TrendResponse
import com.example.analyst.util.Tools
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader

object AnalyzeChartDao {
    private const val TREND = "[TREND]"
    private const val JSON = ".json"
    private var path = ""
    fun setLocalPath(str:String){
        path = str
    }
    fun loadDataFromLocal(code:String): TrendResponse? {
        val dir = File(path)
        for(file in dir.listFiles()){
            if(file.name.contains(code)&&file.name.contains(TREND)){
                val fr = FileReader(file)
                val trend = GsonBuilder().create().fromJson(fr,TrendResponse::class.java)
                fr.close()
                Log.d("Dao","Ask code $code Reading ${file.name}")
                return trend
            }
        }
        return null
    }
}