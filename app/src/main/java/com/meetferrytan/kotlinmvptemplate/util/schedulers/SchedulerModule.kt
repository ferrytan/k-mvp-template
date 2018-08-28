package com.meetferrytan.kotlinmvptemplate.util.schedulers

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Provides common Schedulers used by RxJava
 *
 * @author QuangNguyen (quangctkm9207).
 */
@Module
class SchedulerModule {

    @Module
    companion object {

        @Provides
        @RunOn(SchedulerType.IO)
        @JvmStatic
        fun provideIo(): Scheduler {
            return Schedulers.io()
        }

        @Provides
        @RunOn(SchedulerType.COMPUTATION)
        @JvmStatic
        fun provideComputation(): Scheduler {
            return Schedulers.computation()
        }

        @Provides
        @RunOn(SchedulerType.UI)
        @JvmStatic
        fun provideUi(): Scheduler {
            return AndroidSchedulers.mainThread()
        }

        @Provides
        @JvmStatic
        fun provideSchedulerTransformers(@RunOn(SchedulerType.UI) uiScheduler: Scheduler,
                                         @RunOn(SchedulerType.IO) ioScheduler: Scheduler,
                                         @RunOn(SchedulerType.COMPUTATION) computationScheduler: Scheduler): SchedulerTransformers {
            return SchedulerTransformers(ioScheduler, uiScheduler, computationScheduler)
        }
    }
}