package com.stan.mygithub.commen.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: Stan
 * Date: 2019/8/2 11:52
 * Description: ${DESCRIPTION}
 */
object CommonUtils {
    fun getDateStr(date: Date?): String {
        if(date?.toString() == null){
            return ""
        }else if(date.toString().length < 10){
            return date.toString()
        }
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date).substring(0,10)

    }
}