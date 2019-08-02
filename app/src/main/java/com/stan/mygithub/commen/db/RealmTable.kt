package com.stan.mygithub.commen.db

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Author: Stan
 * Date: 2019/8/2 17:10
 * Description:Realm数据库表
 *              主要保存json的String
 */
/**
 * 用户表
 */
@RealmClass
open class UserInfo: RealmObject(){
    open var userName: String? = null
    open var data: String? = null
}