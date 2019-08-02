package com.stan.mygithub.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.stan.mygithub.R
import com.stan.mygithub.base.BaseFragment
import com.stan.mygithub.been.User
import com.stan.mygithub.commen.config.AppConfig
import com.stan.mygithub.commen.utils.GSYPreference
import com.stan.mygithub.commen.utils.GsonUtils
import com.stan.mygithub.commen.utils.UserConversion
import com.stan.mygithub.module.AppGlobalModel
import kotlinx.android.synthetic.main.fragment_welcome.*
import javax.inject.Inject

/**
 * FileName: WelcomeFragment
 * Author: Stan
 * Date: 2019/7/24 11:17
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class WelcomeFragment: BaseFragment<com.stan.mygithub.databinding.FragmentWelcomeBinding>() {
    private var userInfoStorage : String by GSYPreference(AppConfig.USER_INFO,"")
    /***
     * 委托属性，GSYPreference 把取值和存值的操作代理给 accessTokenStorage
     * 后续的赋值和取值最终是操作的 GSYPreference 得 setValue 和 getValue 函数
     */
    @Inject
    lateinit var appGlobalModel: AppGlobalModel
    private var accessTokenStorage : String by GSYPreference(AppConfig.ACCESS_TOKEN,"")
    override fun getLayoutId(): Int = R.layout.fragment_welcome
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(view: View?) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        welcome_animation.speed =5.0f
        Handler().postDelayed({
            goNext(view)
        },2000)
    }

    private fun goNext(view: View) {
        if(accessTokenStorage.isEmpty()){
            //去登录页
            navigeationPopupTo(view,null,R.id.action_nav_wel_to_login,false)
        }else{
            if(userInfoStorage.isEmpty()){
                navigeationPopupTo(view,null,R.id.action_nav_wel_to_login,false)
            }else{
                val user = GsonUtils.parserJsonToBean(userInfoStorage,User::class.java)
                UserConversion.cloneDataFromUser(context,user,appGlobalModel.userObservable)
                navigeationPopupTo(view, null, R.id.action_nav_wel_to_main, true)
            }
        }
    }
}