package com.zhidian.application.logic.network
import com.google.gson.annotations.SerializedName
data class ChartResponse(
    @SerializedName("买卖")val chase: Chase,
    @SerializedName("分时一日")val hour: Hour,
    @SerializedName("分时五日")val hourOfFiveDay:HourOfFiveDay,
    @SerializedName("周k")val weekData: WeekData,
    @SerializedName("季k")val seasonData: SeasonData,
    @SerializedName("年k")val yearData: YearData,
    @SerializedName("日k")val dayData: DayData,
    @SerializedName("月k")val monthData: MonthData,
    @SerializedName("盘口")val handicap: Handicap
){
    data class Column(
        val timestamp:Long,
        val volume:Long,
        val open:Double,
        val high:Double,
        val low:Double,
        val close:Double,
        val chg:Double,
        val percent:Double,
        val turnoverrate:Double,
        val amount:Double,
        val volume_post:Long,
        val amount_post:Long,
        val pe:Double,
        val pb:Double,
        val ps:Double,
        val pcf:Double,
        val market_capital:String,
        val balance:Double,
        val hold_volume_cn:Double,
        val hold_ratio_cn:Double,
        val net_volume_cn:Double,
        val hold_volume_hk:Double,
        val hold_ratio_hk:Double,
        val net_volume_hk:Double
    )
    data class Chase(
        val data: Data,
        val error_code: Int,
        val error_description: String
    )

    data class Hour(
        val data: DataX,
        val error_code: Int,
        val error_description: String
    )


    data class HourOfFiveDay(
        val data: DataXX,
        val error_code: Int,
        val error_description: String
    )

    data class WeekData(
        val data: DataXXX,
        val error_code: Int,
        val error_description: String
    )

    data class SeasonData(
        val data: DataXXX,
        val error_code: Int,
        val error_description: String
    )

    data class YearData(
        val data: DataXXX,
        val error_code: Int,
        val error_description: String
    )

    data class DayData(
        val data: DataXXX,
        val error_code: Int,
        val error_description: String
    )

    data class MonthData(
        val data: DataXXX,
        val error_code: Int,
        val error_description: String
    )

    data class Handicap(
        val data: DataXXXXXXXX,
        val error_code: Int,
        val error_description: String
    )

    data class Data(
        val market: Market,
        val others: Others,
        val quote: Quote,
        val tags: List<Tag>
    )

    data class Market(
        val delay_tag: Int,
        val region: String,
        val status: String,
        val status_id: Int,
        val time_zone: String,
        val time_zone_desc: String
    )

    data class Others(
        val cyb_switch: Boolean,
        val pankou_ratio: Double
    )

    data class Quote(
        val amount: Double,
        val amplitude: Double,
        val avg_price: Double,
        val chg: Double,
        val code: String,
        val currency: String,
        val current: Double,
        val current_ext: Any,
        val current_year_percent: Double,
        val delayed: Int,
        val dividend: Double,
        val dividend_yield: Double,
        val eps: Double,
        val exchange: String,
        val float_market_capital: Double,
        val float_shares: Double,
        val goodwill_in_net_assets: Any,
        val high: Double,
        val high52w: Double,
        val is_registration: String,
        val is_registration_desc: String,
        val is_vie: Any,
        val is_vie_desc: Any,
        val issue_date: Long,
        val last_close: Double,
        val limit_down: Double,
        val limit_up: Double,
        val lock_set: Int,
        val lot_size: Int,
        val low: Double,
        val low52w: Double,
        val market_capital: Double,
        val name: String,
        val navps: Double,
        val no_profit: Any,
        val no_profit_desc: Any,
        val open: Double,
        val pb: Double,
        val pe_forecast: Double,
        val pe_lyr: Double,
        val pe_ttm: Double,
        val percent: Double,
        val pledge_ratio: Double,
        val profit: Double,
        val profit_forecast: Long,
        val profit_four: Double,
        val security_status: Any,
        val status: Int,
        val sub_type: String,
        val symbol: String,
        val tick_size: Double,
        val time: Long,
        val timestamp: Long,
        val timestamp_ext: Any,
        val total_shares: Double,
        val traded_amount_ext: Any,
        val turnover_rate: Double,
        val type: Int,
        val volume: Int,
        val volume_ext: Any,
        val volume_ratio: Double,
        val weighted_voting_rights: Any,
        val weighted_voting_rights_desc: Any
    )

    data class Tag(
        val description: String,
        val value: Int
    )

    data class DataX(
        val after: List<Any>,
        val items: List<Item>,
        val items_size: Int,
        val last_close: Double
    )

    data class Item(
        val amount: Double,
        val avg_price: Double,
        val capital: Capital,
        val chg: Double,
        val current: Double,
        val high: Double,
        val kdj: Any,
        val low: Double,
        val macd: Any,
        val percent: Double,
        val ratio: Any,
        val timestamp: Long,
        val volume: Int,
        val volume_compare: VolumeCompare
    )

    data class Capital(
        val large: Double,
        val medium: Double,
        val small: Double,
        val xlarge: Double
    )

    data class VolumeCompare(
        val volume_sum: Int,
        val volume_sum_last: Int
    )

    data class DataXX(
        val after: List<Any>,
        val items: List<ItemX>,
        val items_size: Int,
        val last_close: Double
    )

    data class ItemX(
        val amount: Double,
        val avg_price: Double,
        val capital: CapitalX,
        val chg: Double,
        val current: Double,
        val high: Double,
        val kdj: Any,
        val low: Double,
        val macd: Any,
        val percent: Double,
        val ratio: Any,
        val timestamp: Long,
        val volume: Int,
        val volume_compare: VolumeCompareX
    ) {
        data class CapitalX(
            val small:Double,
            val large:Double,
            val xlarge:Double,
            val medium:Double
        )

        data class VolumeCompareX(
            val volume_sum:Double,
            val volume_sum_last:Double
        )
    }

    data class DataXXX(
        val column: List<String>,
        val item: List<List<Double>>,
        val symbol: String
    )

    data class DataXXXXXXXX(
        val ask1_order_list: Any,
        val bc1: Double,
        val bc10: Double,
        val bc2: Double,
        val bc3: Double,
        val bc4: Double,
        val bc5: Double,
        val bc6: Double,
        val bc7: Double,
        val bc8: Double,
        val bc9: Double,
        val bid1_order_list: Double,
        val bn1: Double,
        val bn10: Double,
        val bn2: Double,
        val bn3: Double,
        val bn4: Double,
        val bn5: Double,
        val bn6: Double,
        val bn7: Double,
        val bn8: Double,
        val bn9: Double,
        val bp1: Double,
        val bp10: Double,
        val bp2: Double,
        val bp3: Double,
        val bp4: Double,
        val bp5: Double,
        val bp6: Double,
        val bp7: Double,
        val bp8: Double,
        val bp9: Double,
        val buypct: Double,
        val current: Double,
        val diff: Double,
        val level: Double,
        val ratio: Double,
        val sc1: Double,
        val sc10: Double,
        val sc2: Double,
        val sc3: Double,
        val sc4: Double,
        val sc5: Double,
        val sc6: Double,
        val sc7: Double,
        val sc8: Double,
        val sc9: Double,
        val sellpct: Double,
        val sn1: Double,
        val sn10: Double,
        val sn2: Double,
        val sn3: Double,
        val sn4: Double,
        val sn5: Double,
        val sn6: Double,
        val sn7: Double,
        val sn8: Double,
        val sn9: Double,
        val sp1: Double,
        val sp10: Double,
        val sp2: Double,
        val sp3: Double,
        val sp4: Double,
        val sp5: Double,
        val sp6: Double,
        val sp7: Double,
        val sp8: Double,
        val sp9: Double,
        val symbol: String,
        val timestamp: Long
    )
}
