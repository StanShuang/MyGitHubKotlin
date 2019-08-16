package com.stan.mygithub.repository.net

/**
 * Author: Stan
 * Date: 2019/8/16 10:36
 * Description: 结果回调
 */
interface ResultCallBack<T> {
    fun onPage(first: Int, current: Int, last: Int) {

    }

    fun onSuccess(result: T?)

    fun onCacheSuccess(result: T?) {

    }

    fun onFailure() {

    }
}