package com.androidproject.comarkdown.account.login

/**
 * Created by evan on 2018/1/8.
 */
class LoginPresenter(val loginView:LoginContract.View):LoginContract.Presenter{
    init {
        loginView.presenter = this
    }

    override fun start() {

    }

    override fun login(username: String, password: String) {

    }
}