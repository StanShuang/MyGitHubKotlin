package com.stan.mygithub.module

import com.google.gson.annotations.SerializedName
import com.stan.mygithub.BuildConfig
import java.util.*

/**
 * FileName: LoginRequestModel
 * Author: Stan
 * Date: 2019/7/26 14:21
 * Description: 请求登录的model对象
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LoginRequestModel {
    var scopes: List<String>? = null
        private set
    var note: String? = null
        private set
    @SerializedName("client_id")
    var clientId: String? = null
        private set
    @SerializedName("client_secret")
    var clientSecret: String? = null
        private set

    companion object {
        fun generate(): LoginRequestModel {
            val model = LoginRequestModel()
            model.scopes = Arrays.asList("user", "repo", "gist", "notifications")
            model.note = BuildConfig.APPLICATION_ID
            model.clientId = BuildConfig.CLIENT_ID
            model.clientSecret = BuildConfig.CLIENT_SECRET
            return model
        }
    }


}