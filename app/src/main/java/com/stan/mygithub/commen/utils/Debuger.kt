package com.stan.mygithub.commen.utils

import android.text.TextUtils
import android.util.Log
import com.stan.mygithub.BuildConfig

/**
 * FileName: Debuger
 * Author: Stan
 * Date: 2019/7/25 16:11
 * Description: debug输出
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object Debuger {
    private val TAG = "MygithubKotlin"
    var debugMode =BuildConfig.DEBUG


    fun printfLog(log: String) {
        printfLog(TAG, log)
    }
    fun printfLog(tag: String, log: String?) {
        if (debugMode && log != null) {
            if (!TextUtils.isEmpty(log))
                Log.i(tag, log)
        }
    }

}