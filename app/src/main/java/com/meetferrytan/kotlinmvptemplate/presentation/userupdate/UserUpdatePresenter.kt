package com.meetferrytan.kotlinmvptemplate.presentation.userupdate


import com.meetferrytan.kotlinmvptemplate.base.presentation.BasePresenter
import com.meetferrytan.kotlinmvptemplate.data.repository.remote.UserRestInterface
import com.meetferrytan.kotlinmvptemplate.util.schedulers.SchedulerTransformers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ferrytan on 11/8/17.
 */

class UserUpdatePresenter(
        schedulerTransformers: SchedulerTransformers,
        private val mUserRestInterface: UserRestInterface)
    : BasePresenter<UserUpdateContract.View>(schedulerTransformers), UserUpdateContract.Presenter {
    override fun getUserUpdate(username: String?) {
        val params = HashMap<String, String>()
        params[PARAM_QUERY] = "user:$username+pushed:>${lastWeekDateGithubFormatted()}"
        params[PARAM_SORT] = "updated"

        subscribeSingleRequest(
                mUserRestInterface.searchRepositories(params).map { it.items!! },
                PROCESS_GET_USER_UPDATE,
                dataRequestCallback = { result ->
                    val updateText = if (result.isEmpty()) {
                        "User hasn't pushed anything in the past week"
                    } else {
                        result[0].let { "Last pushed \"${it.name}\" repository at ${formatLastPushedDate(it.pushedAt)}" }
                    }
                    view?.showUserUpdate(updateText)
                })
    }

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

    companion object {
        const val PROCESS_GET_USER_UPDATE = 0
        const val PARAM_QUERY = "q"
        const val PARAM_SORT = "sort"
    }
}