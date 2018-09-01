package com.meetferrytan.kotlinmvptemplate.base.di

import com.meetferrytan.kotlinmvptemplate.base.di.scopes.ActivityScope
import com.meetferrytan.kotlinmvptemplate.presentation.githubusersearch.GithubUserSearchActivity
import com.meetferrytan.kotlinmvptemplate.presentation.githubusersearch.GithubUserSearchBindingModule
import com.meetferrytan.kotlinmvptemplate.presentation.githubusersearch.GithubUserSearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by ferrytan on 11/3/17.
 */
@Module
abstract class AndroidBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [GithubUserSearchBindingModule::class, GithubUserSearchModule::class])
    abstract fun githubUserSearchModule(): GithubUserSearchActivity
}