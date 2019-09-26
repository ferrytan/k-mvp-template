package com.meetferrytan.kotlinmvptemplate.base.presentation

interface BaseContract {

    interface View {
        fun showError(processCode: Int, throwable: Throwable) {
            showLoading(processCode, false)
        }

        fun showLoading(processCode: Int, show: Boolean)
    }

    interface Presenter<V : View> {
        val view: V?

        fun attachView(mvpView: V)

        fun detachView()

        fun isViewBound() = view!=null
    }

}