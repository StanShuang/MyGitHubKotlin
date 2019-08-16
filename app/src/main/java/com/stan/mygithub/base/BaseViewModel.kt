package com.stan.mygithub.base

import android.app.Application
import android.service.carrier.CarrierMessagingService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stan.mygithub.base.holder.LoadState
import com.stan.mygithub.repository.net.ResultCallBack
import org.jetbrains.anko.runOnUiThread

/**
 * Author: Stan
 * Date: 2019/8/8 17:41
 * Description: 基类ViewModel
 */
abstract class BaseViewModel(private val application: Application): ViewModel(),ResultCallBack<ArrayList<Any>> {
    //数据集合
    val dataList = MutableLiveData<ArrayList<Any>>()
    //上拉 下拉状态
    val loading = MutableLiveData<LoadState>()
    //是否需要加载更多
    val needMore = MutableLiveData<Boolean>()
    var lastPage = -1
    var page = 1
    init {
        needMore.value = true
        loading.value = LoadState.NONE
        dataList.value = arrayListOf()
    }
    open fun onRefresh(){
        if(isLoading()){
            return
        }
        page = 1
        loading.value = LoadState.Refresh
        loadDataByRefresh()
    }

    open fun onLoadMore(){
        if (isLoading()){
           return
        }
        page++
        loading.value = LoadState.LoadMore
        loadDataByLoadMore()
    }
    open fun isLoading(): Boolean = loading.value == LoadState.Refresh || loading.value == LoadState.LoadMore
    open fun clearWhenRefresh(){
        if(page <= 1){
            dataList.value = arrayListOf()
            needMore.value = true
        }
    }

    override fun onSuccess(result: ArrayList<Any>?) {
        commitResult(result)
        completeLoadData()
    }

    override fun onCacheSuccess(result: ArrayList<Any>?) {
        application.runOnUiThread {
            result?.apply {
                if(this.isNotEmpty()){
                    commitResult(this)
                }
            }
        }
    }

    override fun onFailure() {
        completeLoadData()
    }

    override fun onPage(first: Int, current: Int, last: Int) {
        if(last != -1){
            lastPage = last
        }
        if(lastPage != -1){
            application.runOnUiThread {
                needMore.value = (page < lastPage)
            }
        }
    }
    private fun completeLoadData() {
        when(loading.value){
            LoadState.Refresh ->{
                loading.value = LoadState.RefreshDone
            }
            LoadState.LoadMore -> {
                loading.value = LoadState.LoadMoreDone
            }
            LoadState.NONE -> {
                loading.value = LoadState.NONE
            }
        }
    }

    private fun commitResult(result: ArrayList<Any>?) {
        result?.apply {
            dataList.value = this
        }
    }
    open fun isFirstData(): Boolean = (page == 1)
    abstract fun loadDataByRefresh()
    abstract fun loadDataByLoadMore()

}