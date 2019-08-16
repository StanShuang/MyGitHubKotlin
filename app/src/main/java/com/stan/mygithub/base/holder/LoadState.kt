package com.stan.mygithub.base.holder

/**
 * Author: Stan
 * Date: 2019/8/16 10:11
 * Description: 上下拉加载状态
 */
enum class LoadState {
    Refresh,
    LoadMore,
    RefreshDone,
    LoadMoreDone,
    NONE
}