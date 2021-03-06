package com.androidproject.comarkdown.network

import android.content.Context
import android.support.annotation.StringRes
import com.androidproject.comarkdown.R

/**
 * Created by evan on 2017/12/31.
 */
enum class ApiErrorType(val code:Int,@param:StringRes private val messageId: Int){
    INTERNAL_SERVER_ERROR(500, R.string.service_error),
    BAD_GATEWAY(502, R.string.service_error),
    BAD_REQUEST(400, R.string.not_found),
    UNAUTHORIZED(401, R.string.not_found),
    FORBIDDEN(403, R.string.not_found),
    NOT_FOUND(404, R.string.not_found),
    CONNECTION_TIMEOUT(408, R.string.timeout),
    NETWORK_NOT_CONNECT(499, R.string.network_wrong),
    UNEXPECTED_ERROR(700, R.string.unexpected_error);

    private val DEFAULT_CODE = 1

    fun getApiErrorModel(context: Context): ApiErrorModel {
        return ApiErrorModel(DEFAULT_CODE, context.getString(messageId))
    }
}