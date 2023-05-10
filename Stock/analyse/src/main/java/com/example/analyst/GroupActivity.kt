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

package com.example.analyst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.analyst.adapter.ViewPage2Adapter
import com.example.analyst.dialog.ItemAddDialog
import com.example.analyst.fragment.GroupFragment
import com.example.analyst.model.AnalystManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        initToolBar()
    }
    private val labelList = listOf(AnalystManager.ANALYST,AnalystManager.MASTER,AnalystManager.FRIEND)
    private val textList = listOf("分析师","投资高手","好友")
    private val fragmentList = ArrayList<Fragment>()
    private fun initToolBar() {
        for(i in 0 until 3){
            val fragment = GroupFragment.newInstance()
            val bundle = Bundle()
            bundle.putString(GroupFragment.LABEL,labelList[i])
            bundle.putString(GroupFragment.TITLE,textList[i])
            fragment.arguments = bundle
            fragmentList.add(fragment)
        }
        val mainTitleTabLayout = findViewById<TabLayout>(R.id.mainTitleTabLayout)
        val analystViewPager = findViewById<ViewPager2>(R.id.analystViewPager)
        analystViewPager.adapter = ViewPage2Adapter(supportFragmentManager,lifecycle,fragmentList)
        val mediator = TabLayoutMediator(mainTitleTabLayout,analystViewPager,true,true){
            tab: TabLayout.Tab, i:Int ->
            if(i in 0 until 3) tab.text = textList[i]
        }
        mediator.attach()
        val analystTitleBackBtn = findViewById<Button>(R.id.analystTitleBackBtn)
        analystTitleBackBtn.setOnClickListener {
            finish()
        }
        val analystTitleAddBtn = findViewById<Button>(R.id.analystTitleAddBtn)
        val dialog = ItemAddDialog(this, themeResId = R.style.ItemAddDialog)
        analystTitleAddBtn.setOnClickListener {
            dialog.show()
        }
    }
}