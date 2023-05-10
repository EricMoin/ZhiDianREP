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

package com.example.notes.adapter

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.edit.EditNotes
import com.example.notes.R
import com.example.notes.card.NotesCard
import com.example.notes.card.TimeCard
import com.example.notes.data.NotesData
import com.example.notes.model.NotesDataManager
import com.example.notes.util.Tools
import java.util.*
import kotlin.collections.ArrayList

class NotesCardAdapter(val context: FragmentActivity, private val notesList:ArrayList<NotesCard>):RecyclerView.Adapter<NotesCardAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var notesYear = view.findViewById<TextView>(R.id.notesYear)
        var notesDate = view.findViewById<TextView>(R.id.notesDate)
        var notesTime = view.findViewById<TextView>(R.id.notesTime)
        var noteTitle = view.findViewById<TextView>(R.id.notesTitle)
        var noteInfo = view.findViewById<TextView>(R.id.notesInfo)
    }
    private var maxPriority = 0
    private var sortStatus = 0
    val startActivity = context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d("NotesCardAdapter","Receive result code ${it.resultCode}")
        if(it.resultCode == RESULT_OK){
            reload()
        }
    }
    public fun getList() = notesList
    public fun reload(){
        val oldSize = notesList.size
        notesList.clear()
        notifyItemRangeRemoved(0,oldSize)
        notesList.addAll(NotesDataManager.getNotesCardList())
        val newSize = notesList.size
        notifyItemRangeInserted(0,newSize)
        sortStatus = Tools.SORT_REVERSE
    }
    public fun toTop(position: Int){
        Collections.swap(notesList,position,0)
        notifyItemMoved(position,0)
        Log.d("NotesCardAdapter","The maxPriority is $maxPriority")
    }
    public fun insertNewItem(){
        NotesDataManager.newNotesData()
        val intent = Intent(context,EditNotes::class.java)
        startActivity.launch(intent)
    }
    public fun removeItem(position: Int){
        NotesDataManager.deleteNotesData(notesList[position].label)
        notesList.removeAt(position)
        notifyItemRemoved(position)
    }
    public fun refreshAll(){
        Tools.sort(notesList,Tools.SORT)
        notifyItemRangeChanged(0,notesList.size)
        sortStatus = Tools.SORT
    }
    public fun reverseAll(){
        sortStatus = sortStatus xor 1
        Tools.sort(notesList,sortStatus)
        notifyItemRangeChanged(0,notesList.size)
    }
    public fun removeAll(){
        val size = notesList.size
        notesList.clear()
        notifyItemRangeRemoved(0,size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notes_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            val notes = notesList[position]
            val intent = Intent(context,EditNotes::class.java)
            NotesDataManager.setCurrentNotesByLabel(notes.label)
            startActivity.launch(intent)
        }
        return holder
    }
    override fun getItemCount() = notesList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notesItem = notesList[position]
        holder.notesYear.text = Tools.fillWithZero(notesItem.time.notesYear)
        holder.notesDate.text =
            Tools.fillWithZero(notesItem.time.notesMonth)+
                    TimeCard.HYPHEN+
                    Tools.fillWithZero( notesItem.time.notesDay)
        holder.notesTime.text = Tools.fillWithZero( notesItem.time.notesHour)+
                TimeCard.COLON+
                Tools.fillWithZero( notesItem.time.notesMinute)
        holder.noteTitle.text = notesItem.noteTitle
        holder.noteInfo.text = notesItem.notesInfo
    }

    fun sortByTime(fromTime:TimeCard,toTimeCard: TimeCard) {
        val oldSize = notesList.size
        val list = Tools.getNotesCardWithRange(fromTime,toTimeCard)
        val newSize = list.size
        notesList.clear()
        notifyItemRangeRemoved(0,oldSize)
        notesList.addAll(list)
        notifyItemRangeInserted(0,newSize)
    }
    fun searchByTitle(content: String) {
        removeAll()
        notesList.addAll( NotesDataManager.getNotesCardListByTitle(content) )
    }
}