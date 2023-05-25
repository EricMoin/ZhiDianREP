package com.zhidian.application.logic.utils

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.zhidian.application.logic.data.StockItem

object Tools {
    var height = 0
    fun updateViewPager2(viewPager2: ViewPager2, fragmentList:ArrayList<Fragment>){
        viewPager2.registerOnPageChangeCallback(
            object:ViewPager2.OnPageChangeCallback() {
                //重写ViewPager2.OnPageChangeCallback()中的onPageSelected
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //这个view是当前被选中的fragment。
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
    fun updatePagerHeightForChild(view: View, pager: ViewPager2) {
        val wMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
        val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(wMeasureSpec, hMeasureSpec)
        if (pager.layoutParams.height != view.measuredHeight) {
            val lp : ViewGroup.LayoutParams = pager.layoutParams
            lp.height = view.measuredHeight
            pager.layoutParams = lp
        }
        height = view.measuredHeight
        Log.d("Tools","view 高度： $height")
    }
    fun sortByPrice(list:ArrayList<StockItem>,way:Int){
        list.sortWith(
            compareBy { it.newestPrice.toFloat() }
        )
        if(way==1) list.reverse()
    }
    fun sortByChg(list:ArrayList<StockItem>,way:Int){
        list.sortWith(
            compareBy { it.range.toFloat() }
        )
        if(way==1) list.reverse()
    }
}
