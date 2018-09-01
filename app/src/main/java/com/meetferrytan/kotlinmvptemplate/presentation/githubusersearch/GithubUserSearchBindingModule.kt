package com.meetferrytan.kotlinmvptemplate.presentation.githubusersearch

import com.meetferrytan.kotlinmvptemplate.base.di.scopes.FragmentScope
import com.meetferrytan.kotlinmvptemplate.presentation.userdetail.UserDetailBindingModule
import com.meetferrytan.kotlinmvptemplate.presentation.userdetail.UserDetailFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by ferrytan on 11/8/17.
 */
@Module
abstract class GithubUserSearchBindingModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [UserDetailBindingModule::class])
    abstract fun userDetailFragment(): UserDetailFragment
}
