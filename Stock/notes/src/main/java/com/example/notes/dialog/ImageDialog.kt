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
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.example.notes.R
import com.github.chrisbanes.photoview.PhotoView


class ImageDialog(activity: Activity,val path:String, themeResId: Int) : Dialog(activity, themeResId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_image_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
        initImage()
    }
    private var mainActivity = activity
    private fun initImage() {
        val notesDialogImage = findViewById<PhotoView>(R.id.notesDialogImage)
        val bitmap = BitmapFactory.decodeFile(path)
        notesDialogImage.setImageBitmap(bitmap)
    }
}