package com.androidproject.comarkdown.account.login

import com.androidproject.comarkdown.data.AccountInfo
import com.androidproject.comarkdown.data.LoginInfo
import com.androidproject.comarkdown.network.ApiClient
import com.androidproject.comarkdown.network.ApiErrorModel
import com.androidproject.comarkdown.network.ApiResponse
import com.androidproject.comarkdown.network.NetworkScheduler

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
        ApiClient.instance.service.login(username, password)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<LoginInfo>(loginView.getViewContext()) {
                    override fun success(data: LoginInfo) {
                        if(data.success == "true"){
                            loginView.loginInfo = data
                            AccountInfo.username = data.username
                            AccountInfo.email = data.email
                            AccountInfo.token = data.token
                        }
                    }

                    override fun fail(statusCode: Int, apiErrorModel: ApiErrorModel) {
                    }
                })
    }
}