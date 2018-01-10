package com.androidproject.comarkdown.markdownedit.invite

import android.content.Context
import com.androidproject.comarkdown.base.BasePresenter
import com.androidproject.comarkdown.base.BaseView

/**
 * Created by evan on 2018/1/11.
 */
interface InviteContract {
    interface View : BaseView<Presenter> {
        var success: Boolean
        fun getViewContext(): Context
    }

    interface Presenter : BasePresenter {
        fun invite(username: String)
    }
}