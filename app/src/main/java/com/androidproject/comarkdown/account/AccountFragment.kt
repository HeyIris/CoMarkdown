package com.androidproject.comarkdown.account

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import kotlinx.android.synthetic.main.activity_sign.*

class AccountFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.activity_sign, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_contain.adapter = AccountPagerAdapter(childFragmentManager)
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
