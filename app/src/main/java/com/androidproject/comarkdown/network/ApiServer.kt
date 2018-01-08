package com.androidproject.comarkdown.network

import com.androidproject.comarkdown.data.LoginInfo
import com.androidproject.comarkdown.data.RegisterInfo
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by evan on 2017/12/31.
 */
interface ApiServer {
    @FormUrlEncoded
    @POST("login/")
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<LoginInfo>

    @FormUrlEncoded
    @POST("register/")
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("email") email: String): Observable<RegisterInfo>
}