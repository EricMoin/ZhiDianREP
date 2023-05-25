package com.example.stock.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stock.card.StockDataCard
import com.example.stock.network.ChartResponse
import com.example.stock.utils.StockDataHelper

class StockActivityViewModel:ViewModel(){
    private val _stockLiveData = MutableLiveData<ChartResponse>()
    val stockLiveData get() = _stockLiveData
    val stockCardList = ArrayList<StockDataCard>()
    fun getStock(code:String,path:String){
        val result = SingleStockRepository.getStock(code)
        if (result!=null) {
            Log.d("ViewModel","Get Stock")
            _stockLiveData.value = result!!
        }
    }
    fun loadStockCard() {
        val quote = stockLiveData.value!!.chase.data.quote
        val list = listOf(
            quote.open,
            quote.high,
            quote.volume,
            quote.last_close,
            quote.low,
            quote.volume_ext,
            quote.turnover_rate,
            quote.pe_ttm,
            quote.market_capital,
            quote.pledge_ratio,
            quote.pe_forecast,
            quote.total_shares,
            quote.pledge_ratio,
            quote.pe_lyr,
            quote.traded_amount_ext,
            quote.limit_up,
            quote.pb,
            quote.float_shares,
            quote.limit_down,
            quote.avg_price,
            quote.amplitude,
            quote.high52w,
            null,
            quote.dividend_yield,
            quote.low52w,
            null,
            null
        )
        for (position in StockDataHelper.labels.indices){
            val label = StockDataHelper.labels[position]
            var data = if(list[position]==null) "0" else list[position].toString()
            data = when(label){
                "成交量" -> String.format("%.2f万股",data.toFloat()/1e4)
                "成交额" -> String.format("%.2f亿",data.toFloat()/1e8)
                "总市值" -> String.format("%.2f",data.toFloat()/1e8)+"亿"
                "总股本" -> String.format("%.2f",data.toFloat()/1e8)+"亿"
                "流通股" -> String.format("%.2f",data.toFloat()/1e8)+"亿"
                "换手率","股息率","委 比","振 幅" -> "$data%"
                else -> String.format("%.2f",data.toFloat())
            }
            stockCardList.add( StockDataCard(StockDataHelper.labels[position], data ) )
        }
    }
}