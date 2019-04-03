package com.meetferrytan.kotlinmvptemplate.presentation.userupdate

import com.meetferrytan.kotlinmvptemplate.base.presentation.BaseContract


/**
 * Created by ferrytan on 11/8/17.
 */

class UserUpdateContract {

    interface View : BaseContract.View {
        fun showUserUpdate(updateText: String)
    }

    interface Presenter : BaseContract.Presenter<UserUpdateContract.View> {
        fun getUserUpdate(username: String?)
    }
}
