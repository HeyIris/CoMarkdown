package com.androidproject.comarkdown.account

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.androidproject.comarkdown.R
import kotlinx.android.synthetic.main.activity_sign.*

class AccountActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_login -> {
                main_contain.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_register -> {
                main_contain.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        main_contain.adapter = AccountPagerAdapter(supportFragmentManager)
        main_contain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            private var menuItem: MenuItem = navigation.menu.getItem(0).setChecked(false)

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                menuItem.setChecked(false)
                menuItem = navigation.menu.getItem(position)
                menuItem.setChecked(true)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
