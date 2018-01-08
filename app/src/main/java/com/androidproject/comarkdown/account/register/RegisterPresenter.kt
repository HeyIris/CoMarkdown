package com.androidproject.comarkdown.account.register

import android.widget.Toast

/**
 * Created by evan on 2018/1/8.
 */
class RegisterPresenter(val registerView: RegisterContract.View):RegisterContract.Presenter {
    init {
        registerView.presenter = this
    }

    override fun start() {

    }

    override fun register(username: String, password: String, repeat_password: String) {

    }
}