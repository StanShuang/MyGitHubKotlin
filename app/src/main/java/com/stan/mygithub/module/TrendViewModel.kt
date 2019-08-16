package com.stan.mygithub.module

import android.app.Application
import com.stan.mygithub.R
import com.stan.mygithub.base.BaseViewModel
import com.stan.mygithub.repository.ReposRepository
import javax.inject.Inject

/**
 * Author: Stan
 * Date: 2019/8/8 17:38
 * Description: 首页的ViewModel
 */
class TrendViewModel  @Inject constructor(private val repository: ReposRepository,application: Application): BaseViewModel(application){
    //tab 的item数据集合
    val sortData: List<List<String>> = listOf(
        application.resources.getStringArray(R.array.trend_language).toList(),
        application.resources.getStringArray(R.array.trend_since).toList())
    //请求数据的参数的集合
    val srotValue: List<List<String>> = listOf(
        application.resources.getStringArray(R.array.trend_language_data).toList(),
        application.resources.getStringArray(R.array.trend_since_data).toList())
    //网络请求的参数集合
    val sortType = arrayListOf(srotValue[0][0],srotValue[1][0])
    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        //数据请求 刷新需要清除数据
        clearWhenRefresh()
        repository.getTread(this,sortType[0],sortType[1])

    }
}