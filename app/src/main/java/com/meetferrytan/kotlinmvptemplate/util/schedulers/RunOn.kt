package com.meetferrytan.kotlinmvptemplate.util.schedulers

import javax.inject.Qualifier

/**
 * Qualifier to define Scheduler type (io, computation, or ui main thread).
 *
 * @author QuangNguyen (quangctkm9207).
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class RunOn(val value: SchedulerType = SchedulerType.IO)