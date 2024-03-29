package com.stan.mygithub.commen.utils

import android.graphics.Color
import android.preference.PreferenceManager
import com.stan.mygithub.R
import com.stan.mygithub.app.GSYGithubApplication

/**
 * Author: Stan
 * Date: 2019/8/2 9:54
 * Description: ${DESCRIPTION}
 */
object SettingUtil {
    private val setting = PreferenceManager.getDefaultSharedPreferences(GSYGithubApplication.instance)

    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = GSYGithubApplication.instance.resources.getColor(R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }
    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        setting.edit().putInt("color", color).apply()
    }
    /**
     * 获取是否开启导航栏上色
     */
    fun getNavBar(): Boolean {
        return setting.getBoolean("nav_bar", false)
    }
    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        setting.edit().putBoolean("switch_nightMode", flag).apply()
    }

    /**
     * 获取是否开启夜间模式
     */
    fun getIsNightMode(): Boolean {
        return setting.getBoolean("switch_nightMode", false)
    }
}