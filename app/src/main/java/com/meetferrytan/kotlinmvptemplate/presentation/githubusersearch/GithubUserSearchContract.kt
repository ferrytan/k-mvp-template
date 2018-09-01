package com.meetferrytan.kotlinmvptemplate.presentation.githubusersearch

import com.meetferrytan.kotlinmvptemplate.base.presentation.BaseContract
import com.meetferrytan.kotlinmvptemplate.data.entity.User

/**
 * Created by ferrytan on 10/22/17.
 */

interface GithubUserSearchContract {

    interface View : BaseContract.View {
        fun showUserDetail(user: User)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getUserDetail(username: String)
    }
}
