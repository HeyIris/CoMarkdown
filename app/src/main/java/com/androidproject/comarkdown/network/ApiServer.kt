package com.androidproject.comarkdown.network

import com.androidproject.comarkdown.data.DownloadInfo
import com.androidproject.comarkdown.data.LoginInfo
import com.androidproject.comarkdown.data.RegisterInfo
import com.androidproject.comarkdown.data.UploadInfo
import io.reactivex.Observable
import retrofit2.http.*
import java.io.File

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

    @FormUrlEncoded
    @POST("upload_file/")
    fun uploadFile(@Field("username") username: String,
                   @Field("token") token: String,
                   @Field("filename") filename: String,
                   @Field("file") file:File): Observable<UploadInfo>

    @FormUrlEncoded
    @POST("download_file/")
    fun downloadFile(@Field("username") username: String,
                   @Field("token") token: String,
                   @Field("filename") filename: String): Observable<DownloadInfo>
}