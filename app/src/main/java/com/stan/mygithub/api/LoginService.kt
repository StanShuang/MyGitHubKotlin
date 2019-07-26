package com.stan.mygithub.api

import com.stan.mygithub.been.AccessToken
import com.stan.mygithub.module.LoginRequestModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * FileName: LoginService
 * Author: Stan
 * Date: 2019/7/26 14:04
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface LoginService {
    @POST("authorizations")
    @Headers("Accept: application/json")
    fun authorizations(@Body authRequestModel: LoginRequestModel): Observable<Response<AccessToken>>
}