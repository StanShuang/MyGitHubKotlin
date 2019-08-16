package com.stan.mygithub.api

import com.stan.mygithub.been.Repository
import com.stan.mygithub.commen.config.AppConfig
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*
import java.util.ArrayList

/**
 * FileName: RepoService
 * Author: Stan
 * Date: 2019/7/25 17:35
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface RepoService {
    @GET("users/{user}/repos")
    fun getUserRepository100StatusDao(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "pushed",
        @Query("per_page") per_page: Int = 100
    ): Observable<Response<ArrayList<Repository>>>

    @GET("users/{user}/starred")
    fun getStarredRepos(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "updated",
        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ) : Observable<Response<ArrayList<Repository>>>
    @GET("https://github.com/trending/{languageType}")
    @Headers("Content-Type: text/plain;charset=utf-8")
    fun getTrendData(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("languageType") languageType: String,
        @Query("since") since: String

    ):Observable<Response<String>>
}