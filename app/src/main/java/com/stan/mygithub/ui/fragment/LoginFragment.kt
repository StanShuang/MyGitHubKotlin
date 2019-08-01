package com.stan.mygithub.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.stan.mygithub.R
import com.stan.mygithub.base.BaseFragment
import com.stan.mygithub.commen.config.AppConfig
import com.stan.mygithub.commen.utils.Debuger
import com.stan.mygithub.commen.utils.GSYPreference
import com.stan.mygithub.databinding.FragmentLoginBinding
import com.stan.mygithub.module.LoginViewModel
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * FileName: LoginFragment
 * Author: Stan
 * Date: 2019/7/25 10:32
 * Description: 登录页
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LoginFragment: BaseFragment<FragmentLoginBinding>() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: LoginViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        exitFull()
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(view: View?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(LoginViewModel :: class.java)
        binding?.loginViewModel = viewModel
        viewModel.loginResult.observe(this, Observer { result ->
            if(result != null && result == true){
                navigeationPopupTo(view, null, R.id.action_nav_login_to_main, true)
            }else{
                activity?.toast(R.string.LoginFailTip)
            }
        })

    }
}