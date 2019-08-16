package com.stan.mygithub.base.binding

import androidx.databinding.DataBindingComponent


/**
 * Author: Stan
 * Date: 2019/8/16 17:48
 * Description: 加载DataBinding的扩展适配器
 */
class LoadDataBindingComponent: DataBindingComponent {
    override fun getMyBindAdapter(): DataBindingExpandUtils.Companion = DataBindingExpandUtils
}