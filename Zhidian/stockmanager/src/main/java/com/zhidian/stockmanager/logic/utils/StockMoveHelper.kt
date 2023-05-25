package com.zhidian.stockmanager.logic.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.zhidian.stockmanager.logic.listener.DragListener

class StockMoveHelper(val onItemTouchListener: MyOnItemTouchListener) :ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) = if (recyclerView.layoutManager is LinearLayoutManager) {
            makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT)
        } else {
            makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0)
        }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        onItemTouchListener.onMove(fromPosition, toPosition)
        return true
    }
    private var isDraggable = false
    fun setIsLongPressDragEnabled(status:Boolean){
        isDraggable = status
    }
    override fun isLongPressDragEnabled() = isDraggable
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        onItemTouchListener.onSelectedChanged(viewHolder,actionState)
    }
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        onItemTouchListener.onClear(viewHolder)
    }
    interface MyOnItemTouchListener{
        fun onMove(fromPosition: Int, toPosition: Int)
        fun onSwiped(position: Int)
        fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int)
        fun onClear(viewHolder: RecyclerView.ViewHolder)
    }
}