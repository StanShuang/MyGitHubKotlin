package com.stan.mygithub.commen.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonParser

/**
 * FileName: GsonUtils
 * Author: Stan
 * Date: 2019/7/25 17:42
 * Description: GSON序列化工具类
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object GsonUtils {
    fun <T> parserJsonToBean(jsonString: String, clazzBean: Class<T>): T {
        if (TextUtils.isEmpty(jsonString)) {
            throw RuntimeException("parserJsonToArrayBean jsonString empty")
        }
        val jsonElement = JsonParser().parse(jsonString)
        if (jsonElement.isJsonNull) {
            throw RuntimeException("parserJsonToArrayBean jsonElement empty")
        }
        if (!jsonElement.isJsonObject) {
            throw RuntimeException("parserJsonToArrayBean is not object")
        }
        return Gson().fromJson(jsonElement, clazzBean)
    }
    fun toJsonString(obj: Any?) :String{
        return if(obj != null){
            Gson().toJson(obj)
        }else{
            throw RuntimeException("obj could not be empty")
        }
    }
}