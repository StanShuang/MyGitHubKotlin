package com.stan.mygithub.repository.dao

import android.app.Application
import com.stan.mygithub.been.User
import com.stan.mygithub.commen.db.FlatMapRealmSaveResult
import com.stan.mygithub.commen.db.FlatTransactionInterface
import com.stan.mygithub.commen.db.UserInfo
import com.stan.mygithub.commen.utils.GsonUtils
import com.stan.mygithub.repository.net.FlatMapResult2Response
import io.realm.RealmQuery
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Inject

/**
 * Author: Stan
 * Date: 2019/8/2 17:12
 * Description: 用户相关数据库操作
 */
class UserDao @Inject constructor(application: Application) {
    /**
     * 保存组织的成员信息
     */
    fun saveUserInfo(response: Response<User>, userName: String) {
        FlatMapRealmSaveResult(response,UserInfo::class.java,object : FlatTransactionInterface<UserInfo>{
            override fun query(q: RealmQuery<UserInfo>): RealmResults<UserInfo> {
                return q.equalTo("userName",userName).findAll()
            }

            override fun onTransavtion(targetObject: UserInfo?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.userName = userName
                targetObject?.data = data
            }

        },true)
    }

}