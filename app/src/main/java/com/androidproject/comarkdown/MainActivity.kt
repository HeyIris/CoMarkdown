package com.androidproject.comarkdown

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.androidproject.comarkdown.filesystem.ActivityFile
import kotlinx.android.synthetic.main.activity_main.*
import com.androidproject.comarkdown.account.AccountFragment
import com.androidproject.comarkdown.filesystem.ActivityFileDownload
import com.androidproject.comarkdown.markdownedit.EditFragment
import com.androidproject.comarkdown.markdownedit.invite.InviteActivity
import com.androidproject.comarkdown.utils.ShakeListener
import com.androidproject.comarkdown.utils.addFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var shakeListener: ShakeListener

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

        addFragment(EditFragment(),main_frame.id)

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
                /*val intent = Intent(this, AccountFragment::class.java)
                startActivity(intent)*/
            }
            R.id.nav_file -> {
                val intent = Intent(this, ActivityFile::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            }
            R.id.nav_option -> {

            }
            R.id.nav_share -> {
                val intent = Intent(this, InviteActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_manage -> {
                val intent = Intent(this, ActivityFileDownload::class.java)
                startActivity(intent)
            }
        }
        item.isChecked = false
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun popInvite(){
        val intent = Intent(this, InviteActivity::class.java)
        startActivity(intent)
    }
}