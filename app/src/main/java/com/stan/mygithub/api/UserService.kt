package com.stan.mygithub.api

import com.stan.mygithub.been.Repository
import com.stan.mygithub.been.User
import com.stan.mygithub.commen.config.AppConfig
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * FileName: UserService
 * Author: Stan
 * Date: 2019/7/25 17:01
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface UserService {
    @GET("user")
    fun getPersonInfo(@Header("forceNetWork") forceNetWork: Boolean): Observable<Response<User>>

    @GET("users/{user}")
    fun getUser(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String
    ): Observable<Response<User>>



}