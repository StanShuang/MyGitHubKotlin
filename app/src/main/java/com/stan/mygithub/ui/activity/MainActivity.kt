package com.stan.mygithub.ui.activity

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private var isFirst: Boolean = true
    /**
     * fragment 列表
     */
    @Inject
    lateinit var mainFragmentList: MutableList<Fragment>
    private var nav_userName : TextView? = null
    private var nav_image : CircleImageView? = null
    override fun attacthLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {
        toolbar.run {
            title = getString(R.string.ActionRecommend)
            setSupportActionBar(this)
        }
        initDrawerLayout()
        bottom_navigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
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
        showFragment(0)
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
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.action_recommend -> {
                showFragment(0)
                toolbar.title = getString(R.string.ActionRecommend)
                true
            }
            R.id.action_navigation -> {
                showFragment(1)
                toolbar.title = getString(R.string.ActionTrend)
                true
            }
            R.id.action_mine -> {
                showFragment(2)
                toolbar.title = getString(R.string.ActionMine)
                true
            }
            else -> {
                false
            }
        }
    }

    private fun showFragment(index: Int) {
        val transition = supportFragmentManager.beginTransaction()
        for(i in 0 until mainFragmentList.size){
            transition.hide(mainFragmentList[i])
            if(isFirst){
                transition.add(R.id.container,mainFragmentList[i])
            }
        }
        isFirst = false
        transition.show(mainFragmentList[index])
        transition.commit()
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }


}
