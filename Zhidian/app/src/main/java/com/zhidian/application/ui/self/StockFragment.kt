package com.zhidian.application.ui.self

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhidian.application.R
import com.zhidian.application.logic.utils.Tools
import com.zhidian.application.ui.adapter.SelfStockAdapter
import com.zhidian.application.ui.dialog.BottomChartDialog

class StockFragment : Fragment() {

    companion object {
        fun newInstance() = StockFragment()
        const val DEFAULT = 0
        const val SELECTED = 1
    }
    val viewModel by lazy { ViewModelProvider(this)[StockViewModel::class.java] }
    private lateinit var mainView:View
    private lateinit var adapter:SelfStockAdapter
    private var priceStatus = DEFAULT
    private var rangeStatus = DEFAULT
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_stock, container, false)
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData(requireActivity().filesDir.absolutePath)
        initBottomDialog()
        initRecyclerView()
        initSubTitle()
    }
    private fun initSubTitle() {
        val priceSubUp = mainView.findViewById<ImageView>(R.id.priceSubUp)
        val priceSubDown = mainView.findViewById<ImageView>(R.id.priceSubDown)
        val rangeSubUp = mainView.findViewById<ImageView>(R.id.rangeSubUp)
        val rangeSubDown = mainView.findViewById<ImageView>(R.id.rangeSubDown)
        val priceSubText = mainView.findViewById<TextView>(R.id.priceSubText)
        val rangeSubText = mainView.findViewById<TextView>(R.id.rangeSubText)
        val default = ColorStateList.valueOf(resources.getColor(R.color.label_grey))
        val selected = ColorStateList.valueOf(resources.getColor(R.color.orange))
        priceSubText.setOnClickListener {
            rangeSubUp.imageTintList = default
            rangeSubDown.imageTintList = default
            rangeStatus = DEFAULT
            rangeSubText.setTextColor(default)
            priceSubUp.imageTintList = if (priceStatus == DEFAULT) selected else default
            priceSubDown.imageTintList = if(priceStatus == DEFAULT) default else selected
            priceSubText.setTextColor(selected)
            Tools.sortByPrice(viewModel.stockList,priceStatus)
            priceStatus = priceStatus xor SELECTED
            adapter.notifyDataSetChanged()
        }
        rangeSubText.setOnClickListener {
            priceSubUp.imageTintList = default
            priceSubDown.imageTintList = default
            priceStatus = DEFAULT
            priceSubText.setTextColor(default)
            rangeSubUp.imageTintList = if (rangeStatus == DEFAULT) selected else default
            rangeSubDown.imageTintList = if (rangeStatus == DEFAULT) default else selected
            rangeSubText.setTextColor(selected)
            Tools.sortByChg(viewModel.stockList,rangeStatus)
            rangeStatus = rangeStatus xor SELECTED
            adapter.notifyDataSetChanged()
        }
    }
    private fun initBottomDialog() {
        val panelBtn = mainView.findViewById<ImageView>(R.id.panelBtn)
        panelBtn.setOnClickListener {
            val selfContainer = requireActivity().findViewById<FrameLayout>(R.id.selfContainer)
            val view = LayoutInflater.from(context).inflate(R.layout.bottom_chart_dialog,selfContainer,false)
            val dialog = BottomChartDialog(view, MATCH_PARENT, WRAP_CONTENT)
            dialog.initTabLayout(this)
            dialog.contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            dialog.height = dialog.contentView.measuredHeight
            dialog.animationStyle = R.style.DialogAnimation
            dialog.isOutsideTouchable = true
            dialog.isFocusable = true
            dialog.showAsDropDown(selfContainer,0,-dialog.height)
            val cover = requireActivity().findViewById<View>(R.id.cover)
            cover.visibility = VISIBLE
            cover.alpha = 0.6f
            dialog.setOnDismissListener {
                cover.visibility = GONE
                true
            }

        }
    }

    private fun initRecyclerView() {
        val selfRecyclerView = mainView.findViewById<RecyclerView>(R.id.selfRecyclerView)
        val layoutManger = LinearLayoutManager(requireActivity())
        selfRecyclerView.layoutManager = layoutManger
        adapter = SelfStockAdapter(this,viewModel.stockList)
        selfRecyclerView.adapter = adapter
        viewModel.refreshStockList()
        adapter.notifyDataSetChanged()
    }
}