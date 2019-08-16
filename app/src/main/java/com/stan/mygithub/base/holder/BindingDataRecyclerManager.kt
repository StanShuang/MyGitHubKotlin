package com.stan.mygithub.base.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.shuyu.commonrecycler.BindRecyclerBaseHolder
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.stan.mygithub.base.binding.LoadDataBindingComponent
import java.lang.reflect.Constructor

/**
 * Author: Stan
 * Date: 2019/8/15 15:46
 * Description: 增加对DataBinding的支持
 */
class BindingDataRecyclerManager: BindSuperAdapterManager() {
    @Suppress("UNCHECKED_CAST")
    override fun <T> contructorHolder(
        context: Context,
        parent: ViewGroup,
        classType: Class<out BindRecyclerBaseHolder>?,
        layoutId: Int
    ): T? {
        var `object`: Constructor<*>? = null
        var contructorFirst = true
        val itemTextBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),layoutId,parent,false,LoadDataBindingComponent())
        try {
            `object` = classType?.getDeclaredConstructor(Context::class.java, View::class.java,ViewDataBinding::class.java)
        }catch (e: NoSuchMethodException){
            contructorFirst = false
        }
        if(!contructorFirst){
            try {
                `object` = classType?.getDeclaredConstructor(View::class.java)
            }catch (e: NoSuchMethodException){

            }
        }
        if(`object` == null){
            throw RuntimeException("Holder Constructor Error For : "+classType?.name)
        }
        try {
            `object`.isAccessible = true
            return if(contructorFirst){
                `object`.newInstance(context,itemTextBinding.root,itemTextBinding) as T
            }else{
                `object`.newInstance(itemTextBinding.root) as T
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}