package com.stan.mygithub.repository

import com.stan.mygithub.api.RepoService
import com.stan.mygithub.api.UserService
import com.stan.mygithub.been.User
import com.stan.mygithub.commen.config.AppConfig
import com.stan.mygithub.commen.utils.Debuger
import com.stan.mygithub.commen.utils.GSYPreference
import com.stan.mygithub.commen.utils.GsonUtils
import com.stan.mygithub.repository.net.FlatMapResponse2Result
import com.stan.mygithub.repository.net.PageInfo
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import io.reactivex.functions.Function


/**
 * 用户相关数据获取
 */
class UserRePository(private val retrofit: Retrofit) {
    private var userInfoStorage : String by GSYPreference(AppConfig.USER_INFO,"")
    /**
     * 获取用户详细信息
     */
    fun getPersonInfoObservable(userName: String? = null): Observable<User> {
        val isLoginUser = userName == null
        //根据是否有用户名，获取第三方用户数据或者当前用户数据
        val userService = if(isLoginUser){
                retrofit.create(UserService :: class.java).getPersonInfo(true)
        }else{
                retrofit.create(UserService :: class.java).getUser(true,userName!!)
        }
        return doUserInfoFlat(userService,isLoginUser)
    }
    /**
     * 用户数据请求、组装、保存
     */
    private fun doUserInfoFlat(service: Observable<Response<User>>, loginUser: Boolean): Observable<User> {
        return service.flatMap {
            FlatMapResponse2Result(it)
        }.flatMap {
            //获取用户star数
            val staredService = retrofit.create(RepoService :: class.java).getStarredRepos(true, it.login!!, 1, "updated", 1)
            val honorService = retrofit.create(RepoService::class.java).getUserRepository100StatusDao(true, it.login!!, 1)
            val startedResponse = staredService.blockingSingle()
            val honorResponse = honorService.blockingSingle()
            val staredPageString = startedResponse.headers().get("page_info")
            if(staredPageString != null){
                val pageInfo =  GsonUtils.parserJsonToBean(staredPageString, PageInfo::class.java)
                it.starRepos = if(pageInfo.last<0){
                    0
                }else{
                    pageInfo.last
                }
            }
            if(honorResponse.isSuccessful){
                val list = honorResponse.body()
                var count = 0
                list?.forEach {
                    count += it.watchersCount
                }
                it.honorRepos = count
            }

            Observable.just(it)
        }.doOnNext {
            //如果是登录用户，保存一份数据到 SharedPreferences
            if(loginUser){
                //保存用户信息
                userInfoStorage = GsonUtils.toJsonString(it)
                //TODO:用户相关实体转换
            }
            //TODO:用户相关数据库操作
        }.onErrorResumeNext(Function<Throwable, Observable<User>> {
            ///拦截错误
            //userInfoStorage = ""
            Debuger.printfLog("userInfo onErrorResumeNext ")
            Observable.error(it)
        })
    }

}

