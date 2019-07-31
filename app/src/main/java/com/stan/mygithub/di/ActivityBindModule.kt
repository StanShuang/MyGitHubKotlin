package com.stan.mygithub.di

import com.stan.mygithub.di.annotation.ActivityScope
import com.stan.mygithub.ui.activity.StartNavigationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * FileName: ActivityBindModule
 * Author: Stan
 * Date: 2019/7/30 11:13
 * Description: Activity 注入，并且提供Fragment注入绑定
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
@Module
abstract class ActivityBindModule {
    @ActivityScope
    //主要作用就是通过 @ContributesAndroidInjector  标记哪个类需要使用依赖注入功能
    //节省代码
    @ContributesAndroidInjector(modules = [StartFragmentBindModule::class])
    abstract fun startNavigationActivityInjector(): StartNavigationActivity

}