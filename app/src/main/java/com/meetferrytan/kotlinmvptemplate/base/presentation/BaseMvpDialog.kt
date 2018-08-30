package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject

abstract class BaseMvpDialog<P : BaseContract.Presenter<V>, V : BaseContract.View> : DialogFragment() {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    protected lateinit var presenter: P
        private set

    protected abstract fun getViewImpl(): V?

    protected abstract fun inject()

    protected abstract fun createLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    protected abstract fun processArguments(args: Bundle?)

    protected abstract fun startingUpDialogFragment(view: View, savedInstanceState: Bundle?)

    @Inject
    fun setupPresenter(presenter: P) {
        this.presenter = presenter
        if (getViewImpl() == null) {
            throw UnsupportedOperationException("Must provide MVP View")
        }
        this.presenter.attachView(getViewImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        lifecycleRegistry = LifecycleRegistry(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = createLayout(inflater, container, savedInstanceState)
        processArguments(arguments)
        startingUpDialogFragment(view, savedInstanceState)
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