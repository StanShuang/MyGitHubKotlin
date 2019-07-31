package com.stan.mygithub.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.launcher.ARouter
import com.stan.mygithub.app.GSYGithubApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * FileName: AppInjector
 * Author: Stan
 * Date: 2019/7/30 10:47
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object AppInjector {
    fun init(application: GSYGithubApplication){
        //通过builder注入application，然后注入app
        DaggerAppComponent.builder().application(application)
            .build().inject(application)
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStarted(p0: Activity) {

            }

            override fun onActivityDestroyed(p0: Activity) {

            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityStopped(p0: Activity) {

            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                handleActivity(p0)
            }

            override fun onActivityResumed(p0: Activity) {

            }

        })
    }

    private fun handleActivity(activity: Activity) {
        //注入activity
        if(activity is HasSupportFragmentInjector){
            AndroidInjection.inject(activity)
        }
        if(activity is ARouterInjectable){
            //注入Arouter参数
            ARouter.getInstance().inject(activity)
        }
        if(activity is FragmentActivity){
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks(){
                    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                        //注入fragment
                        if(f is Injectable){
                            AndroidSupportInjection.inject(f)
                        }
                        if(f is ARouterInjectable){
                            ARouter.getInstance().inject(f)
                        }
                    }

                },true
            )
        }
    }
}