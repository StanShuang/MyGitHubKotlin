package com.stan.mygithub.repository

import android.content.Context
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.stan.mygithub.api.LoginService
import com.stan.mygithub.been.User
import com.stan.mygithub.commen.config.AppConfig
import com.stan.mygithub.commen.utils.Debuger
import com.stan.mygithub.commen.utils.GSYPreference
import com.stan.mygithub.module.LoginRequestModel
import com.stan.mygithub.repository.net.FlatMapResponse2Result
import com.stan.mygithub.repository.net.FlatMapResult2Response
import com.stan.mygithub.repository.net.ResultProgressObserver
import com.stan.mygithub.repository.net.RetrofitFactory
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * FileName: LoginRepository
 * Author: Stan
 * Date: 2019/7/25 15:41
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LoginRepository @Inject constructor(private val retrofit: Retrofit,private val userRepository: UserRePository) {
    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME,"")
    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD,"")
    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN,"")
    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO,"")
    private var userBasicCodeStorage: String by GSYPreference(AppConfig.USER_BASE_CODE,"")

    fun login(context: Context?, username: String, password: String, loginResult: MutableLiveData<Boolean>) {
        clearTokenStorge()
        val type = "$username:$password"
        val base64 =Base64.encodeToString(type.toByteArray(),Base64.NO_WRAP).replace("\\+","%2B")
        Debuger.printfLog("base64Str login $base64")

        val loginService = getTokenObservable()
        val userService = userRepository.getPersonInfoObservable()

        val anthorizations = Observable.zip(loginService,userService,
            BiFunction<String?,User,User> { _, user ->
                user
        }).flatMap {
            FlatMapResult2Response(it)
        }
        RetrofitFactory.executeResult(anthorizations,object : ResultProgressObserver<User>(context!!){
            override fun onSuccess(result: User?) {
                passwordStorage = password
                loginResult.value = true
            }
            override fun onCodeError(code: Int, message: String) {
                loginResult.value = false
            }
            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                loginResult.value = false
            }

        })



    }

    /**
     * 获取token
     */
    private fun getTokenObservable(): Observable<String?>? {
        return retrofit.create(LoginService :: class.java)
            .authorizations(LoginRequestModel.generate())
            .flatMap {
                FlatMapResponse2Result(it)
            }.map {
                it.token ?: null
            }.doOnNext {
                Debuger.printfLog("token $it")
                accessTokenStorage = it!!
            }.onErrorResumeNext(Function <Throwable,Observable<String>>{
                Debuger.printfLog("token onErrorResumeNext ")
                clearTokenStorge()
                Observable.error(it)
            })
    }

    /**
     *清除token
     */
    private fun clearTokenStorge() {
        accessTokenStorage = ""
        userBasicCodeStorage = ""
    }
}