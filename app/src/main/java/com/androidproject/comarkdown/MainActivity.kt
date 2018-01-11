package com.androidproject.comarkdown

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.androidproject.comarkdown.account.AccountFragment
import com.androidproject.comarkdown.filesystem.FileFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.androidproject.comarkdown.account.setting.SettingFragment
import com.androidproject.comarkdown.data.AccountInfo
import com.androidproject.comarkdown.filesystem.FileDownloadFragment
import com.androidproject.comarkdown.markdownedit.EditFragment
import com.androidproject.comarkdown.markdownedit.invite.InviteFragment
import com.androidproject.comarkdown.utils.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var shakeListener: ShakeListener
    private var fragments = ArrayList<Fragment>()

    enum class FragmentType{
        EDIT,
        ACCOUNT,
        SETTING,
        FILE,
        FILEDOWNLOAD,
        INVITE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.inflateHeaderView(R.layout.nav_header_main)

        fragments.add(EditFragment())
        fragments.add(AccountFragment())
        fragments.add(SettingFragment())
        fragments.add(FileFragment())
        fragments.add(FileDownloadFragment())
        fragments.add(InviteFragment())
        showMainFragment(FragmentType.EDIT)

        shakeListener = ShakeListener(this)
    }

    override fun onResume() {
        super.onResume()
        shakeListener.registerSensor()
    }

    override fun onPause() {
        super.onPause()
        shakeListener.unregisterSensor()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_account -> {
                if(AccountInfo.token == ""){
                    showMainFragment(FragmentType.ACCOUNT)
                }else{
                    showMainFragment(FragmentType.SETTING)
                }
            }
            R.id.nav_file -> {
                showMainFragment(FragmentType.FILE)
            }
            R.id.nav_option -> {
                showMainFragment(FragmentType.SETTING)
            }
            R.id.nav_share -> {
                showMainFragment(FragmentType.INVITE)
            }
            R.id.nav_manage -> {
                showMainFragment(FragmentType.FILEDOWNLOAD)
            }
        }
        item.isChecked = false
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun popInvite(){
        showMainFragment(FragmentType.INVITE)
    }

    fun showAccountInfo(){
        val navView = nav_view.getHeaderView(0)
        navView.nav_username.text = AccountInfo.username
        navView.nav_email.text = AccountInfo.email
    }

    fun showMainFragment(pos:FragmentType){
        hideAllFragment()
        var position = when(pos){
            FragmentType.EDIT -> 0
            FragmentType.ACCOUNT -> 1
            FragmentType.SETTING -> 2
            FragmentType.FILE -> 3
            FragmentType.FILEDOWNLOAD -> 4
            FragmentType.INVITE -> 5
        }
        if(fragments[position].isAdded){
            showFragment(fragments[position])
        }else {
            addFragment(fragments[position], R.id.main_frame)
        }
    }

    fun hideAllFragment(){
        for(fragment in fragments){
            if (fragment.isAdded){
                hideFragment(fragment)
            }
        }
    }
}