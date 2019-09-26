package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseInjectionFragment : Fragment(), HasAndroidInjector {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

    protected abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return childFragmentInjector
    }

}