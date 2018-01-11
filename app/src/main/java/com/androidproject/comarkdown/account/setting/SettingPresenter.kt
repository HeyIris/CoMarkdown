package com.androidproject.comarkdown.account.setting


/**
 * Created by evan on 2018/1/11.
 */
class SettingPresenter(val settingView: SettingContract.View):SettingContract.Presenter{
    init {
        settingView.presenter = this
    }
    override fun start() {
        settingView.showAccountInfo()
    }
}