package com.androidproject.comarkdown.markdownedit.contract

import android.widget.TextView
import com.androidproject.comarkdown.base.BasePresenter
import com.androidproject.comarkdown.base.BaseView
import com.androidproject.comarkdown.ot.OTClient

/**
 * Created by evan on 2018/1/7.
 */
interface MdEditContract{
    interface View : BaseView<Presenter>{
        var isActive: Boolean
        var filePath: String
        var opList: ArrayList<String>
        var otClient:OTClient

        fun setTextChangedListener(textView: TextView?)
    }

    interface Presenter : BasePresenter{

    }
}