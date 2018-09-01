package com.meetferrytan.kotlinmvptemplate.data.repository.remote

import com.meetferrytan.kotlinmvptemplate.data.entity.RepositorySearchResponse
import com.meetferrytan.kotlinmvptemplate.data.entity.User
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface UserRestInterface{

    @GET("https://api.github.com/search/repositories")
    fun searchRepositories(@QueryMap(encoded = true) params: Map<String, String>): Flowable<RepositorySearchResponse>


    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Single<User>
}