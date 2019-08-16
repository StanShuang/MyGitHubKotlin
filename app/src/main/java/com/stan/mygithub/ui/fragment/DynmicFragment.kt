package com.stan.mygithub.ui.fragment

import android.view.View
import com.stan.mygithub.R
import com.stan.mygithub.base.BaseFragment
import com.stan.mygithub.commen.utils.Debuger
import com.stan.mygithub.databinding.FragmentDynmicBinding

/**
 * Author: Stan
 * Date: 2019/8/5 10:22
 * Description: 动态
 */
class DynmicFragment : BaseFragment<FragmentDynmicBinding>(){
    override fun onCreateView(view: View?) {
        Debuger.printfLog("This is DynmicFragment")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_dynmic
    }

}