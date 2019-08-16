package com.stan.mygithub.repository

import android.app.Application
import com.stan.mygithub.api.RepoService
import com.stan.mygithub.module.conversion.ReposConversion
import com.stan.mygithub.module.conversion.TrendConversion
import com.stan.mygithub.repository.net.*
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Author: Stan
 * Date: 2019/8/16 13:50
 * Description: 仓库相关数据获取
 */
class ReposRepository @Inject constructor(private val retrofit: Retrofit,private val application: Application) {
    /**
     * 首页数据
     * @param language 语言
     * @param since 日期
     */
    fun getTread(resultCallBack: ResultCallBack<ArrayList<Any>>, language: String, since: String) {
        val treadService = retrofit.create(RepoService ::class.java)
            .getTrendData(true,language,since)
            .flatMap {
                FlatMapResponse2Result(it)
            }.map {
                TrendConversion.htmlToRepo(it)
            }.doOnNext{
                //保存到数据库
            }.map {
                val dataUIList = ArrayList<Any>()
                for(ropoUi in it){
                    dataUIList.add(ReposConversion.trendToReposUIModel(ropoUi))
                }
                dataUIList
            }.flatMap {
                FlatMapResult2Response(it)
            }
        RetrofitFactory.executeResult(treadService,object : ResultTipObserver<ArrayList<Any>>(application){
            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack.onFailure()
            }

        })
    }
}