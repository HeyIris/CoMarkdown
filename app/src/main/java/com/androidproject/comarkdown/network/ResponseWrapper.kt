package com.androidproject.comarkdown.network

/**
 * Created by evan on 2018/1/8.
 */
data class ResponseWrapper<T>(var code: Int, var data: T, var message: String)