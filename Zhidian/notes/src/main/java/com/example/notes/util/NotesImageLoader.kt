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

import android.content.Context
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ns.yc.yccustomtextlib.edit.inter.ImageLoader
import com.ns.yc.yccustomtextlib.edit.view.HyperImageView

class NotesImageLoader(val context: Context) : ImageLoader {
    override fun loadImage(imagePath: String, imageView: HyperImageView, imageHeight: Int) {
        val bitmap = BitmapFactory.decodeFile(imagePath)
        val requestOptions = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
            .asBitmap()
            .load(bitmap)
            .into(imageView)
    }
}