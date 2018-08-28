package com.meetferrytan.kotlinmvptemplate.util.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Created by ferrytan on 11/3/16.
 */

class MyResponseInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
// do something
        return chain.proceed(request)
    }
}