package com.stan.mygithub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stan.mygithub.app.GSYViewModelFactory
import com.stan.mygithub.di.annotation.ViewModelKey
import com.stan.mygithub.module.LoginViewModel
import com.stan.mygithub.module.TrendViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * FileName: ViewModelModule
 * Author: Stan
 * Date: 2019/7/30 10:59
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    // @Binds 类似于 @Provides，在使用接口声明时使用，区别是 @Binds 用于修饰抽象类中的抽象方法的
    // 这个方法必须返回接口或抽象类，比如 ViewModel，不能直接返回 LoginViewModel
    // 方法的参数就是这个方法返回的是注入的对象，类似@Provides修饰的方法返回的对象
    // 这里的 LoginViewModel 会通过上述声明的构造器注入自动构建
    @Binds
    @IntoMap
    //@MapKey的封装注解
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrendViewModel::class)
    abstract fun bindTrendViewModel(trendViewModel: TrendViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: GSYViewModelFactory) :ViewModelProvider.Factory
}