package com.stan.mygithub.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.color.CircleView
import com.stan.learn.utils.StatusBarUtil
import com.stan.mygithub.R
import com.stan.mygithub.commen.utils.SettingUtil

/**
 * FileName: BaseActivity
 * Author: Stan
 * Date: 2019/8/2 9:26
 * Description: 基类activity
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
abstract class BaseActivity:AppCompatActivity() {
    protected var themeColor: Int = SettingUtil.getColor()
    protected abstract  fun attacthLayoutRes() : Int
    abstract  fun initData()
    abstract fun initView()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(attacthLayoutRes())
        initData()
        initView()
    }

    override fun onResume() {
        super.onResume()
        initColor()
    }
    open fun initColor(){
        themeColor = if(!SettingUtil.getIsNightMode()){
            SettingUtil.getColor()
        }else{
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this,themeColor,0)
        if(this.supportActionBar != null){
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(themeColor))
        }else{
            if(SettingUtil.getNavBar()){
                window.navigationBarColor = CircleView.shiftColorDown(themeColor)
            }else{
                window.navigationBarColor = Color.BLACK
            }
        }
    }
}