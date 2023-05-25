package com.zhidian.application.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhidian.application.R
import com.zhidian.application.ui.adapter.ViewPager2Adapter
import com.zhidian.application.ui.search.SearchActivity
import com.zhidian.application.ui.self.NullFragment
import com.zhidian.application.ui.self.SelfSelectFragment
import com.zhidian.stockmanager.logic.dao.GroupDao
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaoRegister.registerPath(this)
        initBottomNav()
    }
    private lateinit var adapter:ViewPager2Adapter
    private val fragmentList = ArrayList<Fragment>()
    private val idList = listOf(R.id.home,R.id.selfSelect,R.id.ground,R.id.chase,R.id.user)
    private fun initBottomNav() {
        fragmentList.add( NullFragment.newInstance() )
        fragmentList.add( SelfSelectFragment.newInstance() )
        fragmentList.add( NullFragment.newInstance() )
        fragmentList.add( NullFragment.newInstance() )
        fragmentList.add( NullFragment.newInstance() )
        val mainBottomNav = findViewById<BottomNavigationView>(R.id.mainBottomNav)
        mainBottomNav.itemIconTintList = null
        val mainViewPager = findViewById<ViewPager2>(R.id.mainViewPager)
        adapter = ViewPager2Adapter(supportFragmentManager,lifecycle,fragmentList)
        mainViewPager.adapter = adapter
        mainViewPager.isUserInputEnabled = false
        mainViewPager.offscreenPageLimit = 5
        mainViewPager.registerOnPageChangeCallback(
            object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    mainBottomNav.menu.getItem(position).isChecked = true
                    super.onPageSelected(position)
                }
            }
        )
        mainBottomNav.setOnItemSelectedListener {
           for(position in idList.indices){
                if(it.itemId==idList[position])mainViewPager.setCurrentItem(position,false)
           }
            true
        }
    }
}