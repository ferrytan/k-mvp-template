package com.meetferrytan.kotlinmvptemplate.presentation.githubusersearch

import com.meetferrytan.kotlinmvptemplate.base.di.scopes.ActivityScope
import com.meetferrytan.kotlinmvptemplate.data.repository.remote.UserRestInterface

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class GithubUserSearchModule {
    @Provides
    @ActivityScope
    fun provideUserRestInterface(retrofit: Retrofit): UserRestInterface {
        return retrofit.create(UserRestInterface::class.java)
    }
}
