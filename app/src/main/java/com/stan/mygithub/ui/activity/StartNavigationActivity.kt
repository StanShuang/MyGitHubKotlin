package com.stan.mygithub.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.stan.mygithub.R
import com.stan.mygithub.commen.config.AppConfig
import com.stan.mygithub.commen.utils.GSYPreference
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * FileName: StartNavigationActivity
 * Author: Stan
 * Date: 2019/7/24 10:35
 * Description: 通过 navigation 实现的首页
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class StartNavigationActivity: AppCompatActivity(),HasSupportFragmentInjector {
    // 当 Fragment 调用 AndroidSupportInjection.inject(this)时
    // 从Activity获取一个DispatchingAndroidInjector<Fragment>，并将Fragment传递给inject(Fragment)
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_navigation)
    }
    //实现 HasSupportFragmentInjector 的接口，表示有Fragment需要注入
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
    var lastTime: Long = 0
    override fun onBackPressed() {
        val fragment = supportFragmentManager.primaryNavigationFragment
        if(fragment is NavHostFragment){
            if(fragment.navController.currentDestination?.id == R.id.loginFragment){
                if(System.currentTimeMillis() - lastTime <= 2000){
                    super.onBackPressed()
                }else{
                    this.toast(R.string.AppOut)
                    lastTime = System.currentTimeMillis()
                }

            }
        }

    }
}