package com.androidproject.comarkdown.markdownedit.contract

import com.androidproject.comarkdown.base.BasePresenter
import com.androidproject.comarkdown.base.BaseView

/**
 * Created by evan on 2018/1/7.
 */
interface MdPreviewContract{
    interface View : BaseView<Presenter> {
        var isActive: Boolean
    }

    interface Presenter : BasePresenter {

    }
}