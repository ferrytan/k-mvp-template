package com.meetferrytan.kotlinmvptemplate.presentation.userdetail

import com.meetferrytan.kotlinmvptemplate.base.di.scopes.ChildFragmentScope
import com.meetferrytan.kotlinmvptemplate.presentation.userupdate.UserUpdateFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by ferrytan on 11/8/17.
 */

@Module
abstract class UserDetailBindingModule {
    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract fun userUpdateFragment(): UserUpdateFragment
}
