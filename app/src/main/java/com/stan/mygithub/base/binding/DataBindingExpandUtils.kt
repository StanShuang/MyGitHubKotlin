package com.stan.mygithub.base.binding

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter


/**
 * FileName: DataBindingExpandUtils
 * Author: Stan
 * Date: 2019/7/24 14:38
 * Description: DataBinding 的拓展适配器
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class DataBindingExpandUtils{
    companion object {
        /**
         * EditText的按键监听
         */
        @BindingAdapter("keyListener")
        fun editTextKeyListener(view: EditText?,listener: View.OnKeyListener){
            view?.apply {
                this.setOnKeyListener(listener)
            }
        }
    }

}
/**
 * 加载DataBinding的扩展适配器
 */
//class GSYDataBindingComponent : DataBindingComponent{
//    override fun getCompanion(): DataBindingExpandUtils.Companion = DataBindingExpandUtils
//}

