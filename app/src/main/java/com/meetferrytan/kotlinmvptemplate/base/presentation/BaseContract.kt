package com.meetferrytan.kotlinmvptemplate.base.presentation

/**
 * Created by ferrytan on 7/4/17.
 */

interface BaseContract {

    interface View {
        fun showError(processCode: Int, throwable: Throwable)

        fun showLoading(processCode: Int, show: Boolean)
    }

    interface Presenter<V : View> {
        val view: V?

        fun attachView(mvpView: V?)

        fun detachView()
    }

}