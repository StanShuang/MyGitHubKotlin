package com.stan.mygithub.base.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.commonrecycler.BindRecyclerBaseHolder

/**
 * Author: Stan
 * Date: 2019/8/15 17:29
 * Description: 基类数据绑定Holder，拓展了对DataBinding支持
 */
abstract class DataBindingHolder<T,D>(context: Context,view: View,private val databind: ViewDataBinding): BindRecyclerBaseHolder(context,view) {
    override fun createView(v: View) {

    }
    @Suppress("UNCHECKED_CAST")
    override fun onBind(model: Any, position: Int) {
        onBind(model as T,position,databind as D)
    }
    abstract fun onBind(model: T,position: Int,databind: D)

}