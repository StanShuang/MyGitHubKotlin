package com.stan.mygithub.ui.activity

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.navigation.NavigationView
import com.stan.mygithub.R
import com.stan.mygithub.base.BaseActivity
import com.stan.mygithub.commen.utils.Debuger
import com.stan.mygithub.commen.view.CircleImageView
import com.stan.mygithub.glide.GlideApp
import com.stan.mygithub.module.AppGlobalModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.image
import javax.inject.Inject

@Suppress("DEPRECATION")
class MainActivity : BaseActivity() , HasSupportFragmentInjector{
    @Inject
    lateinit var appGlobal :AppGlobalModel
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private var nav_userName : TextView? = null
    private var nav_image : CircleImageView? = null
    override fun attacthLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        initDrawerLayout()
        nav_view.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
            nav_userName = getHeaderView(0).findViewById(R.id.tv_username)
            nav_image = getHeaderView(0).findViewById(R.id.tv_image)
        }
        nav_userName?.run {
            if(!appGlobal.userObservable.login.isNullOrEmpty()){
                text = appGlobal.userObservable.login
            }
        }

        if(!appGlobal.userObservable.avatarUrl.isNullOrEmpty()){
            GlideApp.with(this)
                .load(appGlobal.userObservable.avatarUrl)
                .placeholder(R.mipmap.ic_default_avatar)
                .transition(DrawableTransitionOptions().crossFade())
                .into(object  : SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        nav_image?.image = resource
                    }

                })
        }

    }


    private fun initDrawerLayout() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            var params = window.attributes
            params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            drawer_layout.fitsSystemWindows = true
            drawer_layout.clipToPadding = false
        }
        drawer_layout.run {
            var toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }
    private val onDrawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener{

        true
    }
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }


}
