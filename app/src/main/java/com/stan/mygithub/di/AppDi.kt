package com.stan.mygithub.di

import android.app.Application
import com.stan.mygithub.app.GSYGithubApplication
import com.stan.mygithub.repository.net.RetrofitFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * FileName: AppDi
 * Author: Stan
 * Date: 2019/7/30 10:49
 * Description: App级别注入
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
@Singleton
@Component(modules = [
    // 用于绑定扩展的组件，如v4
    // 我们使用的是支持库（v4库）的 Fragment
    // 接入后可以使用  AndroidInjection.inject(activity) 和  AndroidSupportInjection.inject(f)
    AndroidSupportInjectionModule::class,
    ActivityBindModule::class,
    AppModel::class
])
interface AppComponent{
    @Component.Builder
    interface Builder{
        //@BindsInstance注解的作用，只能在 Component.Builder 中使用
        //创建 Component 的时候绑定依赖实例
        @BindsInstance
        fun application(application: Application):Builder

        fun build(): AppComponent
    }

    fun inject(gsyGithubApplication: GSYGithubApplication)
}
@Module(includes = [ViewModelModule::class])
class AppModel{
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit{
        return RetrofitFactory.instance.retrofit
    }
    //TODO 注入数据库

}
