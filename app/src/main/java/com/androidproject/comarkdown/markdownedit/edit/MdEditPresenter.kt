package com.androidproject.comarkdown.markdownedit.edit

import com.androidproject.comarkdown.data.AccountInfo
import com.androidproject.comarkdown.data.PartakeFileInfo
import com.androidproject.comarkdown.markdownedit.edit.MdEditContract
import com.androidproject.comarkdown.network.ApiClient
import com.androidproject.comarkdown.network.ApiErrorModel
import com.androidproject.comarkdown.network.ApiResponse
import com.androidproject.comarkdown.network.NetworkScheduler

/**
 * Created by evan on 2018/1/7.
 */
class MdEditPresenter(val mdEditView: MdEditContract.View) : MdEditContract.Presenter{
    init {
        mdEditView.presenter = this
    }

    override fun start() {

    }

    override fun searchFile(fileName:String) {
        ApiClient.instance.service.partakeFileList(AccountInfo.username, AccountInfo.token)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<PartakeFileInfo>(mdEditView.getViewContext()) {
                    override fun success(data: PartakeFileInfo) {
                        if (data.partake_files != null){
                            for (item in data.partake_files){
                                if(item.name == fileName){
                                    AccountInfo.file = item
                                    break
                                }
                            }
                        }
                    }

                    override fun fail(statusCode: Int, apiErrorModel: ApiErrorModel) {
                    }
                })
    }
}