package com.stan.mygithub.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * FileName: GSYViewModelFactory
 * Author: Stan
 * Date: 2019/7/30 14:19
 * Description: 继承 ViewModelProvider.Factory 实现注入
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
@Singleton
class GSYViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>,@JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull{
            modelClass.isAssignableFrom(it.key)
        } ?. value ?:throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        }catch (e: Exception){
            throw RuntimeException(e)
        }
    }

}