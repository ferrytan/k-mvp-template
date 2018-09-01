package com.meetferrytan.kotlinmvptemplate.presentation.userdetail

import com.meetferrytan.kotlinmvptemplate.base.presentation.BaseContract
import com.meetferrytan.kotlinmvptemplate.data.entity.User

/**
 * Created by ferrytan on 11/8/17.
 */

class UserDetailContract {

    interface View : BaseContract.View {
        fun displayUser(avatar: String?, name: String?, username: String?, location: String?, blog: String?, repos: String?)
    }

    interface Presenter : BaseContract.Presenter<UserDetailContract.View> {
        fun updateUserDetail(user: User)
    }
}
