package com.stan.mygithub.base.binding

import android.graphics.Point
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent
import com.stan.mygithub.commen.utils.CommonUtils
import com.stan.mygithub.commen.utils.dp


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
         * 圆形用户头像加载
         */
        @BindingAdapter("userHeaderUrl",requireAll = false)
        fun loadImage(view: ImageView, url: String?, size: Int = 50){
            CommonUtils.loadUserHeaderImage(view, url ?: "", Point(size.dp, size.dp))
        }
    }

}


