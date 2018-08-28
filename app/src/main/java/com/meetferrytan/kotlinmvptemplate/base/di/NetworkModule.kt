package com.meetferrytan.kotlinmvptemplate.base.di

import android.app.Application
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.meetferrytan.kotlinmvptemplate.BuildConfig
import com.meetferrytan.kotlinmvptemplate.util.interceptors.ConnectivityInterceptor
import com.meetferrytan.kotlinmvptemplate.util.interceptors.MyRequestInterceptor
import com.meetferrytan.kotlinmvptemplate.util.interceptors.MyResponseInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by ferrytan on 10/18/17.
 */

@Module
class NetworkModule {
    @Module
    companion object {
        @Provides
        @Singleton
        @Named(Constants.BASE_URL)
        @JvmStatic
        fun provideBaseUrl(): String {
            return BuildConfig.BASE_URL
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideHttpCache(application: Application): Cache {
            val cacheSize = 10 * 1024 * 1024
            return Cache(application.cacheDir, cacheSize.toLong())
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideOkhttpClient(cache: Cache,
                                requestInterceptor: MyRequestInterceptor,
                                responseInterceptor: MyResponseInterceptor,
                                connectivityInterceptor: ConnectivityInterceptor,
                                httpLoggingInterceptor: HttpLoggingInterceptor,
                                @Named(Constants.NETWORK_TIMEOUT) timeout: Int): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()
                    .addInterceptor(connectivityInterceptor)
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(responseInterceptor)
                    .cache(cache)
                    .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
                    .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                clientBuilder.addInterceptor(httpLoggingInterceptor)
            }
            return clientBuilder.build()
        }

        @Provides
        @Singleton
        @Named(Constants.NETWORK_TIMEOUT)
        @JvmStatic
        fun provideNetworkTimeOut(): Int {
            return 20
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideCiayoResponseInterceptor(): MyResponseInterceptor {
            return MyResponseInterceptor()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideCiayoRequestInterceptor(): MyRequestInterceptor {
            return MyRequestInterceptor()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideConnectivityInterceptor(connectivityManager: ConnectivityManager): ConnectivityInterceptor {
            return ConnectivityInterceptor(connectivityManager)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideRxJavaAdapterFactory(): RxJava2CallAdapterFactory {
            return RxJava2CallAdapterFactory.create()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
            return GsonConverterFactory.create(gson)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideRetrofit(okHttpClient: OkHttpClient,
                            @Named(Constants.BASE_URL) baseUrl: String,
                            gsonConverterFactory: GsonConverterFactory,
                            rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .client(okHttpClient)
                    .build()
        }
    }
}