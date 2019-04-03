package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import javax.inject.Inject

abstract class BaseMvpFragment<P : BaseContract.Presenter<V>, V: BaseContract.View> : BaseInjectionFragment() {
    @Inject
    lateinit var presenter: P

    private var lifecycleRegistry: LifecycleRegistry? = null

    abstract override fun inject()

    protected abstract fun createLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    protected abstract fun processArguments(args: Bundle?)

    protected abstract fun startingUpFragment(savedInstanceState: Bundle?)

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this !is BaseContract.View) {
            throw UnsupportedOperationException("Fragment must implements MVP View")
        }
        lifecycleRegistry = LifecycleRegistry(this)
        this.presenter.attachView(this as V)
        processArguments(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createLayout(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startingUpFragment(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.presenter.detachView()
    }

    override fun getLifecycle(): Lifecycle {
        if(lifecycleRegistry == null)
            lifecycleRegistry = LifecycleRegistry(this)
        return lifecycleRegistry!!
    }
}