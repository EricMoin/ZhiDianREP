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

package com.example.analyst.util

import com.example.analyst.card.TimeCard
import java.util.Calendar

object Tools {
    var path = ""
    fun getTimeCard(): TimeCard {
        val calendar = Calendar.getInstance()
        return TimeCard(
            (calendar.get(Calendar.YEAR)+1).toString(),
            (calendar.get(Calendar.MONTH)).toString(),
            (calendar.get(Calendar.DAY_OF_MONTH)).toString(),
            (calendar.get(Calendar.HOUR_OF_DAY)).toString(),
            (calendar.get(Calendar.MINUTE)).toString()
        )
    }
    fun fillWithZero(str:String) = if( str.toInt() < 10) ("0"+str) else str
    fun toExpressTime(time: TimeCard) :String {
        val defaultDateBuilder = StringBuilder()
        defaultDateBuilder
            .append(time.notesYear)
            .append(TimeCard.HYPHEN)
            .append(fillWithZero(time.notesMonth))
            .append(TimeCard.HYPHEN)
            .append(fillWithZero(time.notesDay))
            .append(TimeCard.SPACE)
            .append(fillWithZero(time.notesHour))
            .append(TimeCard.COLON)
            .append(fillWithZero(time.notesMinute))
        defaultDateBuilder.toString()
        return defaultDateBuilder.toString()
    }

}