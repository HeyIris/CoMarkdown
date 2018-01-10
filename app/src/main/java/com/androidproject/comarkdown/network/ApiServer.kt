package com.androidproject.comarkdown.network

import com.androidproject.comarkdown.data.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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

    @Multipart
    @POST("upload_file/")
    fun uploadFile(@Part("username") username: RequestBody,
                   @Part("token") token: RequestBody,
                   @Part("filename") filename: RequestBody,
                   @Part file: MultipartBody.Part): Observable<UploadInfo>

    @FormUrlEncoded
    @POST("download_file/")
    fun downloadFile(@Field("username") username: String,
                     @Field("token") token: String,
                     @Field("filename") filename: String): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("master_files/")
    fun onlineFileList(@Field("username") username: String,
                       @Field("token") token: String): Observable<OnlineFileInfo>

    @FormUrlEncoded
    @POST("do_OT/{master}@{id}/")
    fun doOT(@Path("master") master: String,
             @Path("id") id:Int,
             @Field("username") username: String,
             @Field("token") token: String,
             @Field("operation") operation: List<String>): Observable<DoOTInfo>

    @FormUrlEncoded
    @POST("create_server/{master}@{id}/")
    fun createServer(@Path("master") master: String,
                     @Path("id") id:Int,
                     @Field("username") username: String,
                     @Field("token") token: String): Observable<CreateServerInfo>
}