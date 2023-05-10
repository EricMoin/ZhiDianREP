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
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.example.notes.R
import com.example.notes.util.TimeCallBackListener
import com.example.notes.card.TimeCard
import com.itheima.wheelpicker.WheelPicker

class DatePickerDialog(activity: Activity, themeResId: Int) : Dialog(activity, themeResId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_wheel_picker)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        window?.setGravity(Gravity.BOTTOM)
        window?.setBackgroundDrawableResource(R.drawable.pop_panel_radius)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        initDataPicker()
        initDataMoreBtn()
    }
    private val mainActivity:Activity = activity
    private fun initDataMoreBtn() {
        val dateMoreBtn = findViewById<TextView>(R.id.dateMoreBtn)
        val moreDialog = DateMorePickerDialog(mainActivity, themeResId = com.google.android.material.R.style.Theme_Material3_DayNight_Dialog)
        moreDialog.dismiss()
        dateMoreBtn.setOnClickListener {
            moreDialog.setTimeListener(
                object :TimeCallBackListener{
                    override fun onTimeCallBack(fromTime: TimeCard, toTime: TimeCard) {
                        timeListener.onTimeCallBack(fromTime,toTime)
                    }
                }
            )
            moreDialog.show()
            dismiss()
        }
    }
    private lateinit var dateYearList:ArrayList<String>
    private lateinit var dateFromMonthList:ArrayList<String>
    private lateinit var dateToMonthList:ArrayList<String>
    private lateinit var dateYearPicker:WheelPicker
    private lateinit var dateFromMonthPicker:WheelPicker
    private lateinit var dateToMonthPicker:WheelPicker
    private lateinit var timeListener: TimeCallBackListener
    public fun setTimeListener(timeListener: TimeCallBackListener){
        this.timeListener = timeListener
    }
    private fun initDataPicker() {
        dateYearList = ArrayList<String>()
        dateFromMonthList = ArrayList<String>()
        dateToMonthList = ArrayList<String>()
        dateYearPicker = findViewById<WheelPicker>(R.id.dateYearPicker)
        dateFromMonthPicker = findViewById<WheelPicker>(R.id.dateFromMonthPicker)
        dateToMonthPicker = findViewById<WheelPicker>(R.id.dateToMonthPicker)
        for(i in 1990..2023){
            val str = StringBuilder()
            str.append(i)
            str.append(TimeCard.YEAR)
            dateYearList.add( str.toString() )
        }
        for(i in 1..12){
            val str = StringBuilder()
            str.append(i)
            str.append(TimeCard.MONTH)
            dateFromMonthList.add( str.toString() )
            dateToMonthList.add( str.toString() )
        }
        dateYearPicker.data = dateYearList
//        dateYearPicker.selectedItemPosition = 2
        dateFromMonthPicker.data = dateFromMonthList
//        dateFromMonthPicker.selectedItemPosition = 2
        dateToMonthPicker.data = dateToMonthList
//        dateToMonthPicker.selectedItemPosition = 2
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isOutOfBounds(context, event)) {
            val fromTime = TimeCard(
                dateYearList[dateYearPicker.currentItemPosition].replace(TimeCard.YEAR,""),
                dateFromMonthList[dateFromMonthPicker.currentItemPosition].replace(TimeCard.MONTH,""),
                "",
                "",
                ""
            )
            val toTime = TimeCard(
                dateYearList[dateYearPicker.currentItemPosition].replace(TimeCard.YEAR,""),
                dateToMonthList[dateToMonthPicker.currentItemPosition].replace(TimeCard.MONTH,""),
                "",
                "",
                ""
            )
            timeListener.onTimeCallBack(fromTime,toTime)
            dismiss()
        }
        return super.onTouchEvent(event)
    }
    private fun isOutOfBounds(context: Context,event: MotionEvent):Boolean{
        val x = event.x
        val y = event.y
        val slop = ViewConfiguration.get(context).scaledWindowTouchSlop
        val decorView = window!!.decorView
        return (x<-slop)||(y<-slop)||(x>(decorView.width+slop+slop))||(y>(decorView.width+slop+slop))
    }
}