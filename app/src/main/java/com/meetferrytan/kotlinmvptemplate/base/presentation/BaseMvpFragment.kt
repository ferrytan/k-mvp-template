package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject

/**
 * Created by ferrytan on 8/2/17.
 */

abstract class BaseMvpFragment<P : BaseContract.Presenter<V>, V : BaseContract.View> : BaseInjectionFragment() {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    protected lateinit var presenter: P
        private set

    protected abstract fun getViewImpl(): V?

    abstract override fun inject()

    protected abstract fun createLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    protected abstract fun processArguments(args: Bundle?)

    protected abstract fun startingUpFragment(view: View, savedInstanceState: Bundle?)

    @Inject
    fun setupPresenter(presenter: P) {
        this.presenter = presenter
        if (getViewImpl() == null) {
            throw UnsupportedOperationException("Must provide MVP View")
        }
        this.presenter.attachView(getViewImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleRegistry = LifecycleRegistry(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = createLayout(inflater, container, savedInstanceState)
        processArguments(arguments)
        startingUpFragment(view, savedInstanceState)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

}