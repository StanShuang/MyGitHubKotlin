package com.stan.mygithub.module

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stan.mygithub.R
import com.stan.mygithub.commen.config.AppConfig
import com.stan.mygithub.commen.utils.GSYPreference
import com.stan.mygithub.repository.LoginRepository
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * FileName: LoginViewModel
 * Author: Stan
 * Date: 2019/7/24 17:02
 * Description: 登录界面的viewModel
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :ViewModel(){
    private var userNameStorage: String by GSYPreference(AppConfig.USER_NAME,"")
    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD,"")
    /**
     * 用户名
     */
    val username = ObservableField<String>()
    /**
     * 密码
     */
    val password = ObservableField<String>()
    /**
     * 登录结果
     *  MutableLiveData<>()数据有变化时，可以通知更新数据
     */
    val loginResult = MutableLiveData<Boolean>()

    init {
        //读取本地存储的用户名以及密码
        username.set(userNameStorage)
        password.set(passwordStorage)
    }
    /**
     * 通过DataBinding在XML绑定的点击方法
     */
    fun onSubmitClick(view: View){
        val username = this.username.get()
        val password = this.password.get()

        username?.apply {
            if(this.isEmpty()){
                view.context.toast(R.string.LoginNameTip)
                return
            }
        }
        password?.apply {
            if(this.isEmpty()){
                view.context.toast(R.string.LoginPWTip)
                return
            }
        }
        //登录
        login(view.context)
    }

    /**
     * 登录执行
     */
    private fun login(context: Context?) {
        loginRepository.login(context,username.get()!!.trim(),password.get()!!.trim(),loginResult)
    }

}