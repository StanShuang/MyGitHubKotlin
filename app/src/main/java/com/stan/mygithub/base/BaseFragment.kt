package com.stan.mygithub.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.stan.mygithub.base.binding.autoCleared
import com.stan.mygithub.di.Injectable
import java.util.*

/**
 * FileName: BaseFragment
 * Author: Stan
 * Date: 2019/7/24 11:33
 * Description: fragment 基类
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
abstract class BaseFragment<T : ViewDataBinding>() : Fragment(),Injectable {

    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<T>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = DataBindingUtil.inflate(
           inflater,
           getLayoutId(),
           container,
           false
          )
        onCreateView(binding?.root)
        return binding?.root
    }
    abstract fun getLayoutId() : Int
    abstract fun onCreateView(view: View?)
    /**
     * Navigation 的页面跳转
     */
    fun navigeationPopupTo(view: View,args: Bundle?,actionId: Int, finishStack: Boolean){
        val controller = Navigation.findNavController(view)
        controller.navigate(actionId,args,NavOptions.Builder().setPopUpTo(controller.graph.id,true).build())
        if(finishStack){
            activity?.finish()
        }
    }
    fun enterFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

    fun exitFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }
}