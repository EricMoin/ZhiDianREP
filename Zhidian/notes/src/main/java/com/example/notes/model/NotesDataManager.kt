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

package com.example.notes.model

import android.util.Log
import com.example.notes.card.NotesCard
import com.example.notes.data.NotesData
import com.example.notes.util.Tools
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.StringBuilder
import java.util.Collections
import java.util.Random

object NotesDataManager {
    var path = ""
    const val NOTES = "[NOTES]"
    const val JSON = ".json"
    private val notesDataList = ArrayList<NotesData>()
    lateinit var currentNotesData:NotesData
    fun loadDataFromLocal(){
        notesDataList.clear()
        val dir = File(path)
        if(dir.listFiles() == null) return
        for(file in dir.listFiles()){
            Log.d("NotesDataManager","${file.name}")
            if(file.name.contains(NOTES)){
                val fr = FileReader(file)
                val notesData = Gson().fromJson(fr,NotesData::class.java)
                fr.close()
                notesDataList.add(notesData)
                Log.d("NotesManager","正在读取 ${notesData.label}")
                currentNotesData = notesData
            }
        }
    }
    fun getNotesDataByLabel(label:String): NotesData? {
        for(notesData in notesDataList){
            if(notesData.label == label) return notesData
        }
        return null
    }
    fun getPositionByLabel(label:String):Int{
        for(i in notesDataList.indices){
            val notesData = notesDataList[i]
            if(notesData.label == label) return i
        }
        return -1
    }
    fun newNotesData(){
        val notesData = NotesData()
        val label = Random().nextLong().toString()
        Log.d("NotesAdapter","New label is $label")
        notesData.label = label
        notesData.priority = 0
        Log.d("NotesAdapter","New list size is ${notesData.notesInfoDataList.size}")
        notesData.notesInfoDataList.last().lastUpdateTime = Tools.getTimeCard()
        notesDataList.add(notesData)
        currentNotesData = notesData
    }
    fun setCurrentNotesByLabel(label:String){
        for(notesData in notesDataList){
            if(notesData.label == label) {
                currentNotesData = notesData
                return
            }
        }
    }
    fun getNotesCardList():ArrayList<NotesCard>{
        val notesCardList = ArrayList<NotesCard>()
        Log.d("NotesDataManager","NotesDataList size is ${notesDataList.size}")
        for(notesData in notesDataList){
            notesCardList.add(Tools.getNotesCard(notesData))
        }
        return notesCardList
    }
    fun writeData(){
        val notesData = currentNotesData
        val fw = FileWriter(File(getFilePath(notesData.label)))
        GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(notesData,fw)
        fw.flush()
        fw.close()
    }
    fun deleteNotesData(label:String) {
        val file = File(getFilePath(label))
        if( file.exists() ) file.delete()
        val position = getPositionByLabel(label)
        notesDataList.removeAt(position)
    }
    fun getNotesCardListByTitle(title:String):ArrayList<NotesCard>{
        val list = ArrayList<NotesCard>()
        for(notesData in notesDataList){
            if(notesData.notesHeaderData.title.contains(title)){
                list.add(Tools.getNotesCard(notesData))
            }
        }
        return list
    }
    fun getFilePath(label:String):String{
        val filePath = StringBuilder()
            .append(path)
            .append(NOTES)
            .append(label)
            .append(JSON)
            .toString()
        return filePath
    }
}