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

package com.example.stock
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.analyst.adapter.ViewPage2Adapter
import com.example.analyst.fragment.AnalyseFragment
import com.example.notes.NotesActivity
import com.example.stock.adapter.StockCardAdapter
import com.example.stock.card.StockDataCard
import com.example.stock.fragment.NullFragment
import com.example.stock.fragment.TimeChartFragment
import com.example.stock.model.StockActivityViewModel
import com.example.stock.utils.StockDataHelper
import com.example.stock.utils.Tools
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File

class StockActivity : AppCompatActivity() {
    companion object{
        const val CODE = "[CODE]"
        const val MAX_LIMITED_PAGES = 7
    }
    val viewModel by lazy { ViewModelProvider(this)[StockActivityViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        DaoRegister.registerPath(this)
        initStockDataPanel()
        addStockData()
        initTimeChartPanel()
        initInformationPanel()
        initBottomMenu()
    }
    private fun initBottomMenu() {
        val stockMenuNotes = findViewById<Button>(R.id.stockMenuNotes)
        stockMenuNotes.setOnClickListener {
            val intent = Intent(applicationContext, NotesActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initTimeChartPanel() {
        val textList = listOf("分时","五日","日K","周K","月K","季K","年K","更多")
        val fragmentList = ArrayList<Fragment>()
        for( i in textList.indices){
            val fragment = TimeChartFragment.newInstance()
            fragmentList.add(fragment)
        }
        val stockDataChartTabLayout = findViewById<TabLayout>(R.id.stockDataChartTabLayout)
        val stockDataChartViewPager = findViewById<ViewPager2>(R.id.stockDataChartViewPager)
        stockDataChartViewPager.adapter = ViewPage2Adapter(supportFragmentManager,lifecycle,fragmentList)
        val mediator = TabLayoutMediator(stockDataChartTabLayout,stockDataChartViewPager,true,true){
                tab: TabLayout.Tab, i: Int ->
                tab.text = textList[i]
        }
        mediator.attach()
        stockDataChartViewPager.isUserInputEnabled = false
    }
    private fun initInformationPanel() {
        val textList = listOf("资讯","走势","估值","资金","分析","扫雷","研报")
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add( NullFragment.newInstance() )
        fragmentList.add( AnalyseFragment.newInstance() )
        for( i in 0 until textList.size-2){
            val fragment = NullFragment.newInstance()
            fragmentList.add(fragment)
        }
        val stockInfoTabLayout = findViewById<TabLayout>(R.id.stockInfoTabLayout)
        val stockInfoViewPager = findViewById<ViewPager2>(R.id.stockInfoViewPager)
        stockInfoViewPager.adapter = ViewPage2Adapter(supportFragmentManager,lifecycle,fragmentList)
        stockInfoViewPager.isUserInputEnabled = false
        Tools.updateViewPager2(stockInfoViewPager,fragmentList)
        val mediator = TabLayoutMediator(stockInfoTabLayout,stockInfoViewPager,false,true){
                tab: TabLayout.Tab, i: Int ->
            tab.text = textList[i]
        }
        mediator.attach()
    }
    private lateinit var adapter : StockCardAdapter
    private fun addStockData() {
        val path = filesDir.absolutePath
        val code = intent.getStringExtra(CODE)?:""
        viewModel.getStock(code,path)
        viewModel.loadStockCard()
        adapter.notifyItemRangeInserted(0,viewModel.stockCardList.size)
    }
    private fun initStockDataPanel() {
        viewModel.stockLiveData.observe(
            this, Observer {
                result ->
                val green = resources.getColor(R.color.label_green)
                val red = resources.getColor(R.color.label_red)
                val quote = result.chase.data.quote
                val priceText = findViewById<TextView>(R.id.priceText)
                priceText.text = String.format("%.2f",quote.current.toFloat())
                val chgText = findViewById<TextView>(R.id.chgText)
                chgText.text = String.format("%.2f",quote.chg.toFloat())
                val chgRatioText = findViewById<TextView>(R.id.chgRatioText)
                chgRatioText.text = String.format("%.2f",(quote.current-quote.last_close)/quote.last_close)+"%"
                val color = if(chgRatioText.text.contains("-")) green else red
                val stockIconText = findViewById<TextView>(R.id.stockIconText)
                stockIconText.text = result.chase.data.tags[0].description
                priceText.setTextColor(color)
                chgText.setTextColor(color)
                chgRatioText.setTextColor(color)
            }
        )
        val stockDataRecyclerView = findViewById<RecyclerView>(R.id.stockDataRecyclerView)
        val layoutManager = GridLayoutManager(this,3)
        stockDataRecyclerView.layoutManager = layoutManager
        adapter = StockCardAdapter(this,viewModel.stockCardList)
        stockDataRecyclerView.adapter = adapter
    }
}