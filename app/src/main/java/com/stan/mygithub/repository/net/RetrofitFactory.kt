package com.stan.mygithub.repository.net

import com.stan.mygithub.BuildConfig
import com.stan.mygithub.commen.config.AppConfig
import com.stan.mygithub.commen.utils.Debuger
import com.stan.mygithub.commen.utils.GSYPreference
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * FileName: RetrofitFactory
 * Author: Stan
 * Date: 2019/7/26 15:17
 * Description: 网络请求
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class RetrofitFactory private constructor(){
    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userBasicCodeStorage: String by GSYPreference(AppConfig.USER_BASE_CODE, "")

    val retrofit: Retrofit
    init {
        val logging = HttpLoggingInterceptor()
        logging.level = if(BuildConfig.DEBUG){
            HttpLoggingInterceptor.Level.BODY
        }else{
            HttpLoggingInterceptor.Level.NONE
        }
        val okHttpClict = OkHttpClient.Builder()
            .connectTimeout(AppConfig.HTTP_TIME_OUT,TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(PageInfoInterceptor())
            .addInterceptor(headerInterceptor())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.GITHUB_API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClict)
            .build()

    }
    /**
     * 拦截头部增加token
     */
    private fun headerInterceptor(): Interceptor {
        return Interceptor {
            var request = it.request()
            val accessToken = getAuthorization()
            Debuger.printfLog("headerInterceptor", accessToken)
            if(!accessToken.isEmpty()){
                Debuger.printfLog(accessToken)
                val url = request.url().toString()
                request = request.newBuilder()
                    .addHeader("Authorization",accessToken)
                    .url(url)
                    .build()
            }

            it.proceed(request)


        }

    }

    private fun getAuthorization(): String {
        if(accessTokenStorage.isBlank()){
            val basic = userBasicCodeStorage
            return if(basic.isBlank()){
                //提示输入账号密码
                ""
            }else{
                "Basic $basic"
            }
        }
        return "token $accessTokenStorage"


    }
    companion object {
        @Volatile
        private var retrofitFactory: RetrofitFactory? = null

        val instance: RetrofitFactory
            get(){
                if(retrofitFactory == null){
                    synchronized(RetrofitFactory :: class.java) {
                        if (retrofitFactory == null) {
                            retrofitFactory = RetrofitFactory()
                        }
                    }
                }
                return retrofitFactory!!
            }

        fun <T> createService(service: Class<T>): T {
            return instance.retrofit.create(service)
        }
        /**
         * 执行请求返回结果
         */
        fun <T> executeResult(observable: Observable<Response<T>>, subscriber: ResultObserver<T>) {
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
        }
    }
}