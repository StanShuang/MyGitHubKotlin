package com.stan.mygithub.module

import com.stan.mygithub.module.data.UserUIModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Stan
 * Date: 2019/8/2 11:34
 * Description: ${DESCRIPTION}
 */
@Singleton
class AppGlobalModel @Inject constructor() {
     val userObservable = UserUIModel()
}