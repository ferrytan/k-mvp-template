package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import javax.inject.Inject

abstract class BaseMvpActivity<P : BaseContract.Presenter<V>, V : BaseContract.View> : BaseInjectionActivity() {
    @Inject
    lateinit var presenter: P

    private var lifecycleRegistry: LifecycleRegistry? = null

    protected abstract fun setLayoutRes(): Int

    protected abstract fun processIntentExtras(extras: Bundle?)

    protected abstract fun startingUpActivity(savedInstanceState: Bundle?)

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleRegistry = LifecycleRegistry(this)
        presenter.attachView(this as V)
        setContentView(setLayoutRes())
        processIntentExtras(intent.extras)
        startingUpActivity(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onRestart() {
        super.onRestart()
        if (!presenter.isViewBound()) {
            presenter.attachView(this as V)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.detachView()
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter.isViewBound()) {
            presenter.detachView()
        }
    }

    override fun getLifecycle(): Lifecycle {
        if (lifecycleRegistry == null)
            lifecycleRegistry = LifecycleRegistry(this)
        return lifecycleRegistry!!
    }
}