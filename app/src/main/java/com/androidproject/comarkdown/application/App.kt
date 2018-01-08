package com.androidproject.comarkdown.application

import android.app.Application
import com.androidproject.comarkdown.network.ApiClient

/**
 * Created by evan on 2018/1/8.
 */
class App: Application(){
    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
    }
}