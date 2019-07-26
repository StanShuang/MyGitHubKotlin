package com.stan.mygithub.base.binding

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * FileName: AutoClearedValue
 * Author: Stan
 * Date: 2019/7/24 13:58
 * Description: 根据Fragment动态清理和获取binding对象
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
/**
 *一个lazy的属性，在fragment被销毁时被清除。
 *
 *在已销毁的fragment中访问此变量将抛出NPE。
 */
class AutoClearedValue<T : Any?>(fragment: Fragment) : ReadWriteProperty<Fragment,T?>{
    var value : T? = null
    init {
        fragment.lifecycle.addObserver(object : LifecycleObserver{
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy(){
                value = null
            }
        })
    }
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return value
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        this.value = value
    }

}

/**
 *创建与此fragment关联的[AutoClearedValue]。
 */
fun <T: Any?> Fragment.autoCleared() = AutoClearedValue<T?>(this)