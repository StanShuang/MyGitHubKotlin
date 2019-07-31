package com.stan.mygithub.di

import com.stan.mygithub.ui.fragment.LoginFragment
import com.stan.mygithub.ui.fragment.WelcomeFragment
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