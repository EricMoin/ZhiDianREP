package com.example.stock.model

import android.util.Log
import com.example.stock.network.ChartResponse
import com.google.gson.Gson
import java.io.File
import java.io.FileReader

object SingleStockDao {
    private const val JSON = ".json"
    private const val STOCK = "[STOCK]"
    private var path = ""
    fun setLocalPath(str:String){
        path = str
    }
    fun getStock(code:String):ChartResponse?{
        val dir = File(path)
        for(file in dir.listFiles()){
            if(file.name.contains(STOCK)){
                val fr = FileReader(file)
                val result = Gson().fromJson(fr, ChartResponse::class.java)
                fr.close()
                Log.d("Dao","Ask code $code")
                Log.d("Dao","Get code ${result.chase.data.quote.code}")
                if( result.chase.data.quote.code == code ) return result
            }
        }
        return null
    }
}