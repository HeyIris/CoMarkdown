package com.androidproject.comarkdown.account.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.properties.Delegates

/**
 * Created by evan on 2018/1/8.
 */
class LoginFragment:Fragment(),LoginContract.View{
    override lateinit var presenter: LoginContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override var loginInfo by Delegates.observable(""){ property, oldValue, newValue ->
        if (newValue[0] == '{'){
            loginSuccess()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_login, null)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_confirm.setOnClickListener { presenter.login(login_username.text.toString(), login_password.text.toString()) }
    }

    override fun getViewContext(): Context {
        return context
    }

    override fun loginSuccess() {

    }
}