package com.stan.mygithub.module.conversion

import com.stan.mygithub.been.TrendingRepoModel
import com.stan.mygithub.module.data.ReposUIModel

/**
 * Author: Stan
 * Date: 2019/8/16 15:03
 * Description: 仓库相关实体转换
 */
object ReposConversion {
    fun trendToReposUIModel(trendModel: TrendingRepoModel): ReposUIModel {
        val reposUIModel = ReposUIModel()
        reposUIModel.hideWatchIcon = true
        reposUIModel.ownerName = trendModel.name
        reposUIModel.ownerPic = trendModel.contributors[0]
        reposUIModel.repositoryDes = trendModel.description
        reposUIModel.repositoryName = trendModel.reposName
        reposUIModel.repositoryFork = trendModel.forkCount
        reposUIModel.repositoryStar = trendModel.starCount
        reposUIModel.repositoryWatch = trendModel.meta
        reposUIModel.repositoryType = trendModel.language
        return reposUIModel
    }

}