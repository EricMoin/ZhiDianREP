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
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.notes.R
class BubbleDialog(val context: Activity): PopupWindow(context){
    init{
        width = 450
        height = 230
        setBackgroundDrawable(ColorDrawable())
        contentView  = LayoutInflater.from(context).inflate(R.layout.notes_bubble_dialog,context.window.decorView as ViewGroup ,false)
        isTouchable = false
        isOutsideTouchable = false
        dismiss()
    }
    public fun show(view:View,x:Int,y:Int){
        context.window.decorView.post{
            showAsDropDown(view,x,y)
        }
    }
}