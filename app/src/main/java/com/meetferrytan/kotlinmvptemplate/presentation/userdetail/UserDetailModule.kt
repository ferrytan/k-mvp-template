package com.meetferrytan.kotlinmvptemplate.presentation.userdetail

import com.meetferrytan.kotlinmvptemplate.base.di.scopes.FragmentScope
import com.meetferrytan.kotlinmvptemplate.util.schedulers.SchedulerTransformers
import dagger.Module
import dagger.Provides


@Module
class UserDetailModule {
    @Provides
    @FragmentScope
    fun provideUserDetailPresenter(schedulerTransformers: SchedulerTransformers): UserDetailContract.Presenter{
        return UserDetailPresenter(schedulerTransformers)
    }
}
