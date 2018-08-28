package com.meetferrytan.kotlinmvptemplate.util.interceptors

/**
 * Created by ferrytan on 9/28/17.
 */

import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val mConnectivityManager: ConnectivityManager) : Interceptor {

    private val isConnected: Boolean
        get() {
            val netInfo = mConnectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw IOException(NO_INTERNET_CONNECTION_MESSAGE)
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    companion object {
        const val NO_INTERNET_CONNECTION_MESSAGE = "Unable to connect to internet"
    }
}