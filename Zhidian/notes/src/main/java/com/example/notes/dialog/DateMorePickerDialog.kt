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

package com.example.notes.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notes.R
import com.example.notes.util.TimeCallBackListener
import com.example.notes.card.TimeCard

class DateMorePickerDialog(activity: Activity, themeResId: Int) : Dialog(activity, themeResId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_more_picker)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawableResource(R.drawable.pop_panel_radius)
        window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
        initBottomBtn()
    }
    private lateinit var timeListener: TimeCallBackListener
    public fun setTimeListener(timeListener: TimeCallBackListener){
        this.timeListener = timeListener
    }
    private fun initBottomBtn() {
        val dateMoreCancel = findViewById<Button>(R.id.dateMoreCancel)
        val dateMoreEnsure = findViewById<Button>(R.id.dateMoreEnsure)
        dateMoreCancel.setOnClickListener {
            dismiss()
        }
        dateMoreEnsure.setOnClickListener {
            saveTime()
        }
    }

    private fun saveTime() {
        val dateStartYearText = findViewById<EditText>(R.id.dateStartYearText)
        val dateStartMonthText = findViewById<EditText>(R.id.dateStartMonthText)
        val dateEndYearText = findViewById<EditText>(R.id.dateEndYearText)
        val dateEndMonthText = findViewById<EditText>(R.id.dateEndMonthText)
        val fromTime = TimeCard(
            dateStartYearText.text.toString(),
            dateStartMonthText.text.toString(),
            "",
            "",
            ""
        )
        val toTime = TimeCard(
            dateEndYearText.text.toString(),
            dateEndMonthText.text.toString(),
            "",
            "",
            ""
        )
        if(
            dateStartYearText.text.isNotEmpty() &&
            dateStartMonthText.text.isNotEmpty() &&
            dateEndYearText.text.isNotEmpty() &&
            dateEndMonthText.text.isNotEmpty()
        ){
            timeListener.onTimeCallBack(fromTime,toTime)
            dismiss()
        }else{
            Toast.makeText(context,"日期不能为空",Toast.LENGTH_SHORT).show()
        }

    }
}