package com.androidproject.comarkdown.account.register

import android.widget.Toast
import com.androidproject.comarkdown.data.RegisterInfo
import com.androidproject.comarkdown.network.ApiClient
import com.androidproject.comarkdown.network.ApiErrorModel
import com.androidproject.comarkdown.network.ApiResponse
import com.androidproject.comarkdown.network.NetworkScheduler

/**
 * Created by evan on 2018/1/8.
 */
class RegisterPresenter(val registerView: RegisterContract.View):RegisterContract.Presenter {
    init {
        registerView.presenter = this
    }

    override fun start() {

    }

    override fun register(username: String, password: String, email: String) {

        ApiClient.instance.service.register(username,password,email)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<RegisterInfo>(registerView.getViewContext()) {
                    override fun success(data: RegisterInfo) {
                        if(data.success == "true"){
                            Toast.makeText(registerView.getViewContext(),"注册成功",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(registerView.getViewContext(),"注册失败",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun fail(statusCode: Int, apiErrorModel: ApiErrorModel) {
                    }
                })
    }
}