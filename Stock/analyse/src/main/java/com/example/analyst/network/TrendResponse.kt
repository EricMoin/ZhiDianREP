/*
 *    @Copyright 2023 EricMoin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.analyst.network

data class TrendResponse(
    val priceOfMin:String,
    val priceOfMax:String,
    val trend:String,
    val price:Double,
    val name:String,
    val kline:List<KLineData>,
    val increase:String,
    val decrease:String,
    val points:Point,
    val status:Int
    ){
    data class Point(val min1:Int,val min2:Int,val max1:Int,val max2:Int)
    data class KLineData(
        val volume:String,
        val deal:String,
        val amplitude:String,
        val closing:String,
        val Chg:String,
        val highest:String,
        val turnoverRate:String,
        val time:String,
        val opening:String,
        val lowest:String,
        val Iad:String
    )
}
