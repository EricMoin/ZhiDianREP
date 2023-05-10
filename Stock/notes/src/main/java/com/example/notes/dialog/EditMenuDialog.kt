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
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.PopupWindow
import com.example.notes.R

class EditMenuDialog(val context:Activity):PopupWindow(context){
    init{
        width = MATCH_PARENT
        height = WRAP_CONTENT
        contentView = LayoutInflater.from(context).inflate(R.layout.notes_edit_menu,context.window.decorView as ViewGroup,false)
        isTouchable = true
        isOutsideTouchable = false
    }
    public fun show(view: View, x:Int, y:Int){
        context.window.decorView.post{
            showAsDropDown(view,x,y)
        }
    }
}