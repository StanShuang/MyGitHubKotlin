package com.stan.mygithub.commen.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.stan.mygithub.app.GSYGithubApplication
import com.stan.mygithub.commen.config.AppConfig
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * FileName: GSYPreference
 * Author: Stan
 * Date: 2019/7/24 16:36
 * Description: 偏好设置
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class GSYPreference<T>(private val keyName: String,private val default: T) : ReadWriteProperty<Any?,T> {
    companion object {
        val prefs: SharedPreferences by lazy { GSYGithubApplication.instance.getSharedPreferences(AppConfig.PREFERENCE_NAME, Context.MODE_PRIVATE) }
        fun clear(){
            prefs.edit().clear().apply()
        }
    }
    private val prefs: SharedPreferences by lazy { GSYGithubApplication.instance.getSharedPreferences(keyName, Context.MODE_PRIVATE) }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T{
        return getSharedPreference(keyName,default)
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T){
        putSharedPreference(keyName,value)
    }
    @SuppressLint("CommitPrefEdits")
    private fun putSharedPreference(keyName: String, value: T) = with(prefs.edit()){
        when(value){
            is Long -> putLong(keyName,value)
            is Int -> putInt(keyName,value)
            is String -> putString(keyName,value)
            is Float -> putFloat(keyName,value)
            is Boolean -> putBoolean(keyName,value)
            else -> {
                throw IllegalArgumentException("Type Error, cannot be saved!")
            }

        }.apply()
    }
    @Suppress("UNCHECKED_CAST")
    private fun getSharedPreference(keyName: String, default: T): T = with(prefs){
        val res: Any = when(default){
            is Long -> getLong(keyName,default)
            is Int -> getInt(keyName,default)
            is String -> getString(keyName,default)
            is Float -> getFloat(keyName,default)
            is Boolean -> getBoolean(keyName,default)
            else ->{
                throw IllegalArgumentException("Type Error,cannot be saved")
            }
        }
        return@with res as T
    }

}