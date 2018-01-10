package com.androidproject.comarkdown.markdownedit.invite

import com.androidproject.comarkdown.data.AccountInfo
import com.androidproject.comarkdown.data.InviteInfo
import com.androidproject.comarkdown.data.PartakeFileInfo
import com.androidproject.comarkdown.network.ApiClient
import com.androidproject.comarkdown.network.ApiErrorModel
import com.androidproject.comarkdown.network.ApiResponse
import com.androidproject.comarkdown.network.NetworkScheduler

/**
 * Created by evan on 2018/1/11.
 */
class InvitePresenter(val inviteView:InviteContract.View): InviteContract.Presenter{
    init {
        inviteView.presenter = this
    }

    override fun start() {

    }

    override fun invite(username:String) {
        if (AccountInfo.filepath != ""){
            ApiClient.instance.service.invite(AccountInfo.username,AccountInfo.token,AccountInfo.file.name,username)
                    .compose(NetworkScheduler.compose())
                    .subscribe(object : ApiResponse<InviteInfo>(inviteView.getViewContext()) {
                        override fun success(data: InviteInfo) {
                            inviteView.success = (data.success == "true")
                        }

                        override fun fail(statusCode: Int, apiErrorModel: ApiErrorModel) {
                            inviteView.success = false
                        }
                    })
        }
    }
}