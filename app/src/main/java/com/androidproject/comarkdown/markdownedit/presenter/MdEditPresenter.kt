package com.androidproject.comarkdown.markdownedit.presenter

import com.androidproject.comarkdown.markdownedit.contract.MdEditContract

/**
 * Created by evan on 2018/1/7.
 */
class MdEditPresenter(val mdEditView: MdEditContract.View) : MdEditContract.Presenter{
    init {
        mdEditView.presenter = this
    }

    override fun start() {
        
    }
}