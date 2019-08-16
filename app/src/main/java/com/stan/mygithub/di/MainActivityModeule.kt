package com.stan.mygithub.di

import androidx.fragment.app.Fragment
import com.stan.mygithub.ui.fragment.DynmicFragment
import com.stan.mygithub.ui.fragment.MyFragment
import com.stan.mygithub.ui.fragment.TrendFragment
import dagger.Module
import dagger.Provides

/**
 * Author: Stan
 * Date: 2019/8/5 11:40
 * Description: ${DESCRIPTION}
 */
@Module
class MainActivityModeule {
    @Provides
    fun providerMainFragmentList(): List<Fragment>{
        return listOf(TrendFragment(),DynmicFragment(),MyFragment())
    }
}