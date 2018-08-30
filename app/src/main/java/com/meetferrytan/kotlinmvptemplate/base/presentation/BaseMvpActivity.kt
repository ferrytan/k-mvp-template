package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import butterknife.ButterKnife
import javax.inject.Inject

abstract class BaseMvpActivity<P : BaseContract.Presenter<V>, V : BaseContract.View> : BaseActionBarActivity() {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    protected lateinit var presenter: P
        private set

    protected abstract fun getViewImpl(): V?

    protected abstract fun setLayoutRes(): Int

    protected abstract fun processIntentExtras(extras: Bundle?)

    protected abstract fun startingUpActivity(savedInstanceState: Bundle?)

    @Inject
    fun setupPresenter(presenter: P) {
        this.presenter = presenter
        if (getViewImpl() == null) {
            throw UnsupportedOperationException("Must provide MVP View")
        }
        this.presenter.attachView(getViewImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutRes())
        ButterKnife.bind(this)
        lifecycleRegistry = LifecycleRegistry(this)
        processIntentExtras(intent.extras)
        startingUpActivity(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}