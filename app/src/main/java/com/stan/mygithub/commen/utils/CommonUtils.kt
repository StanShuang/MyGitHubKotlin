package com.stan.mygithub.commen.utils

import android.graphics.Point
import android.widget.ImageView
import com.stan.mygithub.R
import com.stan.mygithub.commen.imgLoadler.StanImageLoaderManager
import com.stan.mygithub.commen.imgLoadler.StanLoadOption
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: Stan
 * Date: 2019/8/2 11:52
 * Description: 时间日期格式化
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
    fun getDatetoString(date: Long?) : String{
        val format = SimpleDateFormat("yy-MM-dd")
        return format.format(Date(date!!))
    }
    /**
     * 加载用户头像
     */
    fun loadUserHeaderImage(imageView: ImageView, url: String, size: Point = Point(50.dp, 50.dp)) {
        val option = StanLoadOption()
            .setDefaultImg(R.drawable.logo)
            .setErrorImg(R.drawable.logo)
            .setCircle(true)
            .setSize(size)
            .setUri(url)
        StanImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }
}