package com.meetferrytan.kotlinmvptemplate.presentation.userdetail

import com.meetferrytan.kotlinmvptemplate.base.presentation.BasePresenter
import com.meetferrytan.kotlinmvptemplate.data.entity.User
import com.meetferrytan.kotlinmvptemplate.util.schedulers.SchedulerTransformers

/**
 * Created by ferrytan on 11/8/17.
 */

class UserDetailPresenter(schedulerTransformers: SchedulerTransformers)
    : BasePresenter<UserDetailContract.View>(schedulerTransformers), UserDetailContract.Presenter {

    override fun updateUserDetail(user: User) {
        val avatar = user.avatar
        val name = user.name
        val blog = user.blog
        val location = user.location
        val username = user.login
        val reposcount = "${user.publicRepos} public repositories"

        view?.displayUser(avatar, name, username, blog, location, reposcount)
    }
}