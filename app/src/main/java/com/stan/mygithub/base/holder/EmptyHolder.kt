package com.stan.mygithub.base.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.stan.mygithub.R
import com.stan.mygithub.been.EmptyUIModel
import com.stan.mygithub.databinding.LayoutEmptyBinding

/**
 * Author: Stan
 * Date: 2019/8/15 17:36
 * Description: ${DESCRIPTION}
 */
class EmptyHolder(context: Context, private val view: View,dataBind: ViewDataBinding ): DataBindingHolder<EmptyUIModel, LayoutEmptyBinding>(context,view,dataBind) {
    override fun onBind(model: EmptyUIModel, position: Int, databind: LayoutEmptyBinding) {

    }

    override fun createView(v: View) {
        super.createView(v)
    }
    companion object {
        const val ID = R.layout.layout_empty
    }
}