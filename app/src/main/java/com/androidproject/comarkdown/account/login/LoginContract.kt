package com.androidproject.comarkdown.account.login

import android.content.Context
import com.androidproject.comarkdown.base.BasePresenter
import com.androidproject.comarkdown.base.BaseView

/**
 * Created by evan on 2018/1/8.
 */
interface LoginContract{
    interface View : BaseView<Presenter> {
        var isActive: Boolean

        var loginInfo: String

        fun getViewContext(): Context

        fun loginSuccess()
    }

    interface Presenter : BasePresenter {
        fun login(username: String, password: String)
    }
}