package com.androidproject.comarkdown.markdownedit.preview

import com.androidproject.comarkdown.markdownedit.preview.MdPreviewContract

/**
 * Created by evan on 2018/1/7.
 */
class MdPreviewPresenter(val mdPreviewView : MdPreviewContract.View) : MdPreviewContract.Presenter{
    init {
        mdPreviewView.presenter = this
    }

    override fun start() {

    }
}