package com.androidproject.comarkdown.markdownedit

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.data.AccountInfo
import com.androidproject.comarkdown.data.LoginInfo
import com.androidproject.comarkdown.filesystem.FileFragment
import com.androidproject.comarkdown.filesystem.FileDownloadFragment
import com.androidproject.comarkdown.markdownedit.edit.MdEditFragment
import com.androidproject.comarkdown.markdownedit.invite.InviteFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import com.androidproject.comarkdown.utils.ShakeListener


class EditActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var loginInfo:LoginInfo
    private lateinit var shakeListener:ShakeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val navView = nav_view.inflateHeaderView(R.layout.nav_header_main)
        val adapter = EditViewPagerAdapter(supportFragmentManager)
        edit_view_pager.adapter = adapter
        processExtraData()

        shakeListener = ShakeListener(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
        processExtraData()
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
                //val intent = Intent(this, AccountFragment::class.java)
                //startActivity(intent)
            }
            R.id.nav_file -> {
                val intent = Intent(this, FileFragment::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            }
            R.id.nav_option -> {
            }
            R.id.nav_share -> {
                val intent = Intent(this, InviteFragment::class.java)
                startActivity(intent)
            }
            R.id.nav_manage -> {
                val intent = Intent(this, FileDownloadFragment::class.java)
                startActivity(intent)
            }
        }
        item.isChecked = false
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun popInvite(){
        val intent = Intent(this, InviteFragment::class.java)
        startActivity(intent)
    }

    private fun processExtraData(){
        val navView = nav_view.getHeaderView(0)
        val bundle = this.intent.extras
        if(bundle.getString("username") != null){
            loginInfo = LoginInfo(bundle.getString("username"),bundle.getString("email"),"true",bundle.getString("token"))
            navView.nav_username.text = loginInfo.username
            navView.nav_email.text = loginInfo.email
        }

        if(bundle.getString("data") != null) {
            ((edit_view_pager.adapter as EditViewPagerAdapter).view_list[0] as MdEditFragment).filePath = bundle.getString("data")
        }

        if (bundle.getString("master") != null){
            ((edit_view_pager.adapter as EditViewPagerAdapter).view_list[0] as MdEditFragment).filePath = bundle.getString("data")
            AccountInfo.file.master = bundle.getString("master")
            AccountInfo.file.name = bundle.getString("filename")
        }
    }
}
