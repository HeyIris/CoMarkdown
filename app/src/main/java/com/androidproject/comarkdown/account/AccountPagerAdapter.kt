package com.androidproject.comarkdown.account

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.androidproject.comarkdown.account.login.LoginContract
import com.androidproject.comarkdown.account.login.LoginFragment
import com.androidproject.comarkdown.account.login.LoginPresenter
import com.androidproject.comarkdown.account.register.RegisterContract
import com.androidproject.comarkdown.account.register.RegisterFragment
import com.androidproject.comarkdown.account.register.RegisterPresenter

/**
 * Created by evan on 2018/1/8.
 */
class AccountPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var viewList: ArrayList<Fragment>

    init {
        viewList = ArrayList<Fragment>()
        viewList.add(LoginFragment())
        LoginPresenter(viewList[0] as LoginContract.View)
        viewList.add(RegisterFragment())
        RegisterPresenter(viewList[1] as RegisterContract.View)
    }

    override fun getCount(): Int = viewList.size

    override fun getItem(position: Int): Fragment = viewList.get(position)
}