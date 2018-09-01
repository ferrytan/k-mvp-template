package com.meetferrytan.kotlinmvptemplate.presentation.githubusersearch

import com.meetferrytan.kotlinmvptemplate.base.presentation.BasePresenter
import com.meetferrytan.kotlinmvptemplate.data.entity.User
import com.meetferrytan.kotlinmvptemplate.data.repository.remote.UserRestInterface
import com.meetferrytan.kotlinmvptemplate.util.callback.DataRequestCallback
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ferrytan on 10/22/17.
 */

class GithubUserSearchPresenter @Inject
constructor(private val mUserRestInterface: UserRestInterface) : BasePresenter<GithubUserSearchContract.View>(), GithubUserSearchContract.Presenter {

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate: state")
        // presenter state still not restored
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart: state")
        // presenter state still not restored
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume: state")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause: state")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop: state")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: state")
        // all disposable processes are cleared here
    }

    override fun getUserDetail(username: String) {
        subscribeSingleRequest(
                mUserRestInterface.getUserDetail(username),
                PROCESS_GET_USER_DETAIL,
                object : DataRequestCallback<User> {
                    override fun onRequestSuccess(result: User) {
                        view?.showUserDetail(result)
                    }
                }
        )
    }

    companion object {
        const val PROCESS_GET_USER_DETAIL = 101
    }
}