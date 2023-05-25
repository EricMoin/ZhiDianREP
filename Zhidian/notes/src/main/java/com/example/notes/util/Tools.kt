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

package com.example.notes.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.notes.card.NotesCard
import com.example.notes.card.TimeCard
import com.example.notes.data.NotesData
import com.example.notes.model.NotesDataManager
import com.google.gson.Gson
import com.ns.yc.yccustomtextlib.utils.HyperLibUtils
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.collections.ArrayList

object Tools {
    const val SORT = 0
    const val SORT_REVERSE = 1
    const val ZERO = "0"
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
    public fun toTitleTime(time: TimeCard) :String {
        val defaultDateBuilder = StringBuilder()
        defaultDateBuilder
            .append(time.notesYear)
            .append(TimeCard.YEAR)
            .append(time.notesMonth)
            .append(TimeCard.MONTH)
        return defaultDateBuilder.toString()
    }
    public fun getTimeCard(): TimeCard {
        val calendar = Calendar.getInstance()
        return TimeCard(
            (calendar.get(Calendar.YEAR)+1).toString(),
            (calendar.get(Calendar.MONTH)).toString(),
            (calendar.get(Calendar.DAY_OF_MONTH)).toString(),
            (calendar.get(Calendar.HOUR_OF_DAY)).toString(),
            (calendar.get(Calendar.MINUTE)).toString()
        )
    }
    public fun sort(list:ArrayList<NotesCard>,way:Int){
        list.sortWith(
            compareBy(
                {it.priority},
                {it.time.notesYear},
                {it.time.notesMonth},
                {it.time.notesDay},
                {it.time.notesHour},
                {it.time.notesMinute}
            )
        )
        if(way == SORT) list.reverse()
    }
    fun fillWithZero(str:String) = if( str.toInt() < 10) (ZERO+str) else str
    fun getNotesCard(notesData:NotesData):NotesCard{
        val year = notesData.notesInfoDataList[0].lastUpdateTime.notesYear
        val month = notesData.notesInfoDataList[0].lastUpdateTime.notesMonth
        val day = notesData.notesInfoDataList[0].lastUpdateTime.notesDay
        val hour = notesData.notesInfoDataList[0].lastUpdateTime.notesHour
        val minute =  notesData.notesInfoDataList[0].lastUpdateTime.notesMinute
        val title =  notesData.notesHeaderData.title
        val info = notesData.notesInfoDataList[0].content[0]
        val label = notesData.label
        val priority = notesData.priority
        return NotesCard(TimeCard(year,month,day,hour,minute),title,info,priority,label)
    }
    fun applyForReadPermission(context: Activity){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1);
        }
    }
    fun applyForWritePermission(context: Activity){
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1);
        }
    }
     fun applyForInternetPermission(context: Activity){
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.INTERNET),1);
        }
    }
    fun getNotesCardWithRange(fromTime:TimeCard,toTime: TimeCard):ArrayList<NotesCard> {
        val list = ArrayList<NotesCard>()
        for(note in NotesDataManager.getNotesCardList()){
            var flag = true
            flag = flag and (note.time.notesYear.toInt() in fromTime.notesYear.toInt()..toTime.notesYear.toInt())
            flag = flag and (note.time.notesMonth.toInt() in fromTime.notesMonth.toInt()..toTime.notesMonth.toInt())
            if(flag) list.add(note)
            Log.d("Tools","flag is $flag")
        }
        return list
    }
    fun hideKeyboard(activity: Activity) = HyperLibUtils.hideSoftInput(activity)
}