package com.stan.mygithub.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.mikepenz.iconics.Iconics
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.stan.mygithub.BuildConfig
import com.stan.mygithub.R
import com.stan.mygithub.commen.db.RealmFactory
import com.stan.mygithub.commen.style.GSYIconfont
import com.stan.mygithub.commen.utils.CommonUtils
import com.stan.mygithub.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import javax.inject.Inject

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
    @Inject
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
        ARouter.init(this)
        AppInjector.init(this)
        ///初始化图标库
        Iconics.init(applicationContext)
        Iconics.registerFont(GSYIconfont)
        //初始化数据库
        Realm.init(this)
        RealmFactory.instance

        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {

            override fun placeholder(ctx: Context): Drawable {
                return getDrawable(R.drawable.logo)
            }

            override fun placeholder(ctx: Context, tag: String?): Drawable {
                return getDrawable(R.drawable.logo)
            }
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
                CommonUtils.loadUserHeaderImage(imageView, uri.toString())
            }
        })
    }
    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}