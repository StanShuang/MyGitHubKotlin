package com.stan.mygithub.repository.net

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
data class PageInfo(
    var prev: Int = -1,
    var next: Int = -1,
    var last: Int = -1,
    var first: Int = -1
)