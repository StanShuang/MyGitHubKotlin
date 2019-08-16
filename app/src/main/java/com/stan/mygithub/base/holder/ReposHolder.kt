package com.stan.mygithub.base.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.stan.mygithub.R
import com.stan.mygithub.databinding.LayoutReposItemBinding
import com.stan.mygithub.module.data.ReposUIModel

/**
 * Author: Stan
 * Date: 2019/8/16 9:39
 * Description: ${DESCRIPTION}
 */
class ReposHolder(context: Context,private val view: View,dataBinding: ViewDataBinding): DataBindingHolder<ReposUIModel,LayoutReposItemBinding>(context,view,dataBinding) {
    override fun onBind(model: ReposUIModel, position: Int, databind: LayoutReposItemBinding) {
        databind.reposUIModel = model
        //TODO 点击事件


    }
    companion object {
        const val ID = R.layout.layout_repos_item
    }
}