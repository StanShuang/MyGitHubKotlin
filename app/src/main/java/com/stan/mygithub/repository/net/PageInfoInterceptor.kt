package com.stan.mygithub.repository.net

import android.net.Uri
import com.stan.mygithub.commen.utils.GsonUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * FileName: PageInfoInterceptor
 * Author: Stan
 * Date: 2019/7/25 17:41
 * Description: 页面信息提取拦截
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class PageInfoInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val linkString = response.headers()["Link"]
        val pageInfo = PageInfo()
        linkString?.apply {
            val link = this.split(",")
            link.forEach {
                when{
                    it.contains("prev") -> {
                        pageInfo.prev = pardeNumber(it)
                    }
                    it.contains("next") -> {
                        pageInfo.next = pardeNumber(it)
                    }
                    it.contains("last") -> {
                        pageInfo.last = pardeNumber(it)
                    }
                    it.contains("first") -> {
                        pageInfo.first = pardeNumber(it)
                    }


                }
            }
        }
        return response.newBuilder().addHeader("page_info",GsonUtils.toJsonString(pageInfo)).build()
    }

    private fun pardeNumber(item: String?): Int {
        if(item == null){
            return -1
        }
        val startFlag = "<"
        val endFlag = ">"
        val startIndex =item.indexOf(startFlag)
        val endIndex = item.indexOf(endFlag)
        if(startIndex <=0 || endIndex <= 0){
            return -1
        }
        val startStart = startIndex + startFlag.length
        val url = item.substring(startStart,endIndex)
        val value = Uri.parse(url).getQueryParameter("page")
        return  value.toInt()

    }
}
data class PageInfo(
    var prev: Int = -1,
    var next: Int = -1,
    var last: Int = -1,
    var first: Int = -1
)