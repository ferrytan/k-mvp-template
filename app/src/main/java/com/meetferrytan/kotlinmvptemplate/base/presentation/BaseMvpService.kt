package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.app.Service

import javax.inject.Inject

abstract class BaseMvpService<P : BaseContract.Presenter<V>, V : BaseContract.View> : Service() {

    protected lateinit var presenter: P
        private set

    protected abstract fun getViewImpl(): V?

    protected abstract fun inject()

    override fun onCreate() {
        inject()
        super.onCreate()
        setupService()
    }

    abstract fun setupService()

    @Inject
    fun setupPresenter(presenter: P) {
        this.presenter = presenter
        if (getViewImpl() == null) {
            throw UnsupportedOperationException("Must provide MVP Service")
        }
        this.presenter.attachView(getViewImpl())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}