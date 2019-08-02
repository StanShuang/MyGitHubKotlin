package com.stan.mygithub.commen.db

import io.realm.*
import retrofit2.Response

/**
 * Author: Stan
 * Date: 2019/8/2 17:18
 * Description: ${DESCRIPTION}
 */
/**
 * 保存response中的实体信息
 */
class FlatMapRealmSaveResult<T,E:RealmModel>(response: Response<T>,private val clazz: Class<E>,private val listener: FlatTransactionInterface<E> ,needSave: Boolean){
    init {
        if(response.isSuccessful && needSave){
            val realm =Realm.getDefaultInstance()
            realm.executeTransaction {
                val result = listener.query(it.where(clazz))
                val commitTarget = if(result.isNotEmpty()){
                    result[0]
                }else{
                    it.createObject(clazz)
                }
                listener.onTransavtion(commitTarget)
            }
        }
    }

}
interface FlatTransactionInterface<E: RealmModel>{
    fun query(q: RealmQuery<E>):RealmResults<E>
    fun onTransavtion(targetObject: E?)
}