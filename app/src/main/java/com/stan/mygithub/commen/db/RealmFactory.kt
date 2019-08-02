package com.stan.mygithub.commen.db

import android.os.Build
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Author: Stan
 * Date: 2019/8/2 16:29
 * Description: Realm数据初始化
 */
class RealmFactory private constructor(){
    companion object {
        private const val VERSION =1L
        @Volatile
        private var mRealFactory: RealmFactory? = null
        val instance: RealmFactory
            get() {
                if(mRealFactory == null){
                    synchronized(RealmFactory :: class.java){
                        if(mRealFactory == null){
                            mRealFactory = RealmFactory()
                        }
                    }
                }
                return mRealFactory!!

            }
        fun getRealmObservable(): Observable<Realm> {
            return Observable.create{
                val observableRealm = Realm.getDefaultInstance()
                it.onNext(observableRealm)
                it.onComplete()
            }
        }


    }
    init {
        val config = RealmConfiguration.Builder()
            .name("gbInfo.realm")
            .schemaVersion(VERSION)
            .build()
        Realm.setDefaultConfiguration(config)

    }
}