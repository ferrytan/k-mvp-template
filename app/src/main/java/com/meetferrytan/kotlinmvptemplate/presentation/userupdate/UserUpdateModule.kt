package com.meetferrytan.kotlinmvptemplate.presentation.userupdate

import com.meetferrytan.kotlinmvptemplate.base.di.scopes.ChildFragmentScope
import com.meetferrytan.kotlinmvptemplate.data.repository.remote.UserRestInterface
import com.meetferrytan.kotlinmvptemplate.util.schedulers.SchedulerTransformers
import dagger.Module
import dagger.Provides

@Module
class UserUpdateModule {
    @Provides
    @ChildFragmentScope
    fun provideUserDetailPresenter(schedulerTransformers: SchedulerTransformers, userRestInterface: UserRestInterface): UserUpdateContract.Presenter {
        return UserUpdatePresenter(schedulerTransformers, userRestInterface)
    }
}
