package com.meetferrytan.kotlinmvptemplate.presentation.userupdate


import com.meetferrytan.kotlinmvptemplate.base.presentation.BasePresenter
import com.meetferrytan.kotlinmvptemplate.data.entity.GithubRepository
import com.meetferrytan.kotlinmvptemplate.data.repository.remote.UserRestInterface
import com.meetferrytan.kotlinmvptemplate.util.callback.DataRequestCallback
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by ferrytan on 11/8/17.
 */

class UserUpdatePresenter @Inject
constructor(private val mUserRestInterface: UserRestInterface) : BasePresenter<UserUpdateContract.View>(), UserUpdateContract.Presenter {

    private fun lastWeekDateGithubFormatted(): String {
        val lastWeek = Calendar.getInstance()
        lastWeek.add(Calendar.DAY_OF_WEEK, -7)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val lastWeekFormattedDate = simpleDateFormat.format(lastWeek.time)
        Timber.d(lastWeekFormattedDate)
        return lastWeekFormattedDate
    }

    private fun formatLastPushedDate(lastPushedDate: Date?): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM HH:mm:ss.SSS", Locale.getDefault())
        return simpleDateFormat.format(lastPushedDate)
    }

    override fun getUserUpdate(username: String?) {
        val params = HashMap<String, String>()
        params[PARAM_QUERY] = "user:$username+pushed:>${lastWeekDateGithubFormatted()}"
        params[PARAM_SORT] = "updated"
        subscribeFlowableRequest<GithubRepository>(
                mUserRestInterface.searchRepositories(params)
                        .flatMapIterable { it.items }
                        .take(1),
                PROCESS_GET_USER_UPDATE,
                object : DataRequestCallback<GithubRepository> {
                    override fun onRequestSuccess(result: GithubRepository) {
                        val updateText = "Last pushed \"${result.name}\" repository at ${formatLastPushedDate(result.pushedAt)}"
                        view?.showUserUpdate(updateText)
                    }
                })
    }

    companion object {
        const val PROCESS_GET_USER_UPDATE = 0
        const val PARAM_QUERY = "q"
        const val PARAM_SORT = "sort"
    }
}