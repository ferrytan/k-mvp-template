package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import javax.inject.Inject

abstract class BaseMvpActivity<P : BaseContract.Presenter<V>, V: BaseContract.View> : BaseInjectionActivity(){
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
        this.presenter.attachView(this as V)
        setContentView(setLayoutRes())
        processIntentExtras(intent.extras)
        startingUpActivity(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter.detachView()
    }

    override fun getLifecycle(): Lifecycle {
        if(lifecycleRegistry == null)
            lifecycleRegistry = LifecycleRegistry(this)
        return lifecycleRegistry!!
    }
}