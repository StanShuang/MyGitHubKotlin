package com.stan.mygithub.di

import com.stan.mygithub.ui.fragment.*
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * FileName: ActivityScope
 * Author: Stan
 * Date: 2019/7/30 11:19
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
@Module
abstract class StartFragmentBindModule{
    @ContributesAndroidInjector
    abstract fun contributeWelcomFragment(): WelcomeFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment
}
@Module
abstract class MianFragmentBindModeule{
    //主要作用就是通过 @ContributesAndroidInjector  标记哪个类需要使用依赖注入功能
    //节省代码
    @ContributesAndroidInjector
    abstract fun contributeTrendFragment(): TrendFragment

    @ContributesAndroidInjector
    abstract fun contributeDynmicFragment(): DynmicFragment

    @ContributesAndroidInjector
    abstract fun contributeMyFragment(): MyFragment



}