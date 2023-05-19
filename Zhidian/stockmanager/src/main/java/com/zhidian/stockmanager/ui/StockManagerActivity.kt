package com.zhidian.stockmanager.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zhidian.stockmanager.ui.groupmanage.ManageGroupFragment
import com.zhidian.stockmanager.R

class StockManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_manager)
        initTitle()
    }
    private fun initTitle() {
        val stockMangerBack = findViewById<ImageView>(R.id.stockMangerBack)
        stockMangerBack.setOnClickListener { finish() }
        val manageStockBtn = findViewById<TextView>(R.id.manageStockBtn)
        val manageGroupBtn = findViewById<TextView>(R.id.manageGroupBtn)
        val fragment = ManageFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.manageContainer,fragment)
        transaction.commit()
        val selected = ColorStateList.valueOf(Color.parseColor("#F88C3A"))
        val default = ColorStateList.valueOf(Color.parseColor("#000000"))
        manageStockBtn.setTextColor(selected)
        manageStockBtn.setBackgroundResource(R.drawable.manager_text_label)
        manageStockBtn.setOnClickListener {
            val fragment = ManageFragment.newInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.manageContainer,fragment)
            transaction.commit()
            manageGroupBtn.setBackgroundResource(R.color.transparent)
            manageGroupBtn.setTextColor(default)
            manageStockBtn.setBackgroundResource(R.drawable.manager_text_label)
            manageStockBtn.setTextColor(selected)
        }
        manageGroupBtn.setOnClickListener {
            val fragment = ManageGroupFragment.newInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.manageContainer,fragment)
            transaction.commit()
            manageStockBtn.setBackgroundResource(R.color.transparent)
            manageStockBtn.setTextColor(default)
            manageGroupBtn.setBackgroundResource(R.drawable.manager_text_label)
            manageGroupBtn.setTextColor(selected)
        }
    }
}