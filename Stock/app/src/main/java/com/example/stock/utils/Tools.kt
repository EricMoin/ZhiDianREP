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

package com.example.stock.utils

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class Tools {
    companion object{
        public fun updateViewPager2(viewPager2: ViewPager2,fragmentList:ArrayList<Fragment>){
            viewPager2.registerOnPageChangeCallback(
                object:ViewPager2.OnPageChangeCallback() {
                    //重写ViewPager2.OnPageChangeCallback()中的onPageSelected
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        //这个view是的fragment。
                        val view = fragmentList[position].view
                        view?.let {
                            updatePagerHeightForChild(view,viewPager2)
                        }
                    }
                }
            )
            viewPager2.viewTreeObserver.addOnGlobalLayoutListener {
                val view = fragmentList[viewPager2.currentItem].view
                view?.let {
                    updatePagerHeightForChild(view,viewPager2)
                }
            }
        }
        //解决viewpager2高度问题
        public fun updatePagerHeightForChild(view: View, pager: ViewPager2) {
            val wMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(wMeasureSpec, hMeasureSpec)
            if (pager.layoutParams.height != view.measuredHeight) {
                val lp : ViewGroup.LayoutParams = pager.layoutParams
                lp.height = view.measuredHeight
                pager.layoutParams = lp
            }
        }
    }
}