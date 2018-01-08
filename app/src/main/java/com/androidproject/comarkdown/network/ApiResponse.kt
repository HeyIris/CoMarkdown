package com.androidproject.comarkdown.network

import android.content.Context
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by evan on 2017/12/31.
 */
abstract class ApiResponse<T>(private val context:Context) : Observer<T> {
    abstract fun success(data: T)
    abstract fun fail(statusCode: Int, apiErrorModel: ApiErrorModel)

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
        success(t)
    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) {
            val apiErrorModel: ApiErrorModel = when (e.code()) {
                ApiErrorType.INTERNAL_SERVER_ERROR.code ->
                    ApiErrorType.INTERNAL_SERVER_ERROR.getApiErrorModel(context)
                ApiErrorType.BAD_GATEWAY.code ->
                    ApiErrorType.BAD_GATEWAY.getApiErrorModel(context)
                ApiErrorType.NOT_FOUND.code ->
                    ApiErrorType.NOT_FOUND.getApiErrorModel(context)
                else -> otherError(e)
            }
            fail(e.code(), apiErrorModel)
            return
        }

        val apiErrorType:ApiErrorType = when(e){
            is UnknownHostException -> ApiErrorType.NETWORK_NOT_CONNECT
            is ConnectException -> ApiErrorType.NETWORK_NOT_CONNECT
            is SocketTimeoutException -> ApiErrorType.CONNECTION_TIMEOUT
            else -> ApiErrorType.UNEXPECTED_ERROR
        }
    }

    private fun otherError(e: HttpException) = Gson().fromJson(e.response().errorBody()?.charStream(), ApiErrorModel::class.java)
}