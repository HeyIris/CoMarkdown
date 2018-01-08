package com.androidproject.comarkdown.account.register

import android.content.Context
import com.androidproject.comarkdown.base.BasePresenter
import com.androidproject.comarkdown.base.BaseView

/**
 * Created by evan on 2018/1/8.
 */
interface RegisterContract{
    interface View : BaseView<Presenter> {
        var isActive: Boolean

        fun getViewContext(): Context
    }

    interface Presenter : BasePresenter {
        fun register(username: String, password: String, repeat_password: String)
    }
}