package com.meetferrytan.kotlinmvptemplate.util.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by ferrytan on 12/21/16.
 */

//    private static final String HEADER_AUTHORIZATION = "Authorization";
//    private static final String AUTH_PREFIX = "Bearer ";

class MyRequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request

        /*String token = mSession.getUserToken();
        if (!"".equals(token)) {
            newRequest = request.newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, AUTH_PREFIX + token)
                    .build();
            return chain.proceed(newRequest);
        }*/
        return chain.proceed(request)
    }
}