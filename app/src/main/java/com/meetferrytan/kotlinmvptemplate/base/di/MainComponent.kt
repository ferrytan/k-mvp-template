package com.meetferrytan.kotlinmvptemplate.base.di

import com.meetferrytan.kotlinmvptemplate.MyApplication
import com.meetferrytan.kotlinmvptemplate.util.schedulers.SchedulerModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by ferrytan on 10/20/17.
 */

@Component(modules = [
    BaseModule::class,
    NetworkModule::class,
    SchedulerModule::class,
    AndroidBindingModule::class,
    AndroidSupportInjectionModule::class])
@Singleton
interface MainComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}