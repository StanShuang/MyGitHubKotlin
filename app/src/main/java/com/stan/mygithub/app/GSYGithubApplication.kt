package com.stan.mygithub.app

import android.app.Activity
import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.stan.mygithub.BuildConfig
import com.stan.mygithub.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlin.properties.Delegates

/**
 * FileName: GSYGithubApplication
 * Author: Stan
 * Date: 2019/7/24 16:40
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class GSYGithubApplication: Application(),HasActivityInjector {

    companion object {
        var instance: GSYGithubApplication by Delegates.notNull()
    }
    /**
     * 分发Activity的注入
     *
     * 在Activity调用AndroidInjection.inject(this)时
     * 从Application获取一个DispatchingAndroidInjector<Activity>，并将activity传递给inject(activity)
     * DispatchingAndroidInjector通过AndroidInjector.Factory创建AndroidInjector
     */
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        instance = this
        //初始化路由
        if(BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        //Application级别注入
        AppInjector.init(this)
        ARouter.init(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}