package com.androidproject.comarkdown.account.setting

import android.content.Context
import com.androidproject.comarkdown.base.BasePresenter
import com.androidproject.comarkdown.base.BaseView

/**
 * Created by evan on 2018/1/11.
 */
interface SettingContract{
    interface View : BaseView<Presenter> {
        var isActive: Boolean
        fun getViewContext(): Context
    }

    interface Presenter : BasePresenter {
    }
}