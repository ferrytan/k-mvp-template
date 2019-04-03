package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import javax.inject.Inject

abstract class BaseMvpDialogFragment<P : BaseContract.Presenter<V>, V : BaseContract.View> : DialogFragment() {
    @Inject
    lateinit var presenter: P

    private var lifecycleRegistry: LifecycleRegistry? = null

    protected abstract fun inject()

    protected abstract fun createLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    protected abstract fun processArguments(args: Bundle?)

    protected abstract fun startingUpDialogFragment(savedInstanceState: Bundle?)

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        if (this !is BaseContract.View) {
            throw UnsupportedOperationException("Dialog Fragment must implements MVP View")
        }
        lifecycleRegistry = LifecycleRegistry(this)
        this.presenter.attachView(this as V)
        processArguments(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = createLayout(inflater, container, savedInstanceState)
        processArguments(arguments)
        startingUpDialogFragment(savedInstanceState)
        return view
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