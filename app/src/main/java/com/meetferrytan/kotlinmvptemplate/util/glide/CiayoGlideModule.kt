package com.meetferrytan.kotlinmvptemplate.util.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * Created by ferrytan on 11/14/16.
 */

@GlideModule
class CiayoGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(15, TimeUnit.SECONDS) // connect timeout
        builder.readTimeout(15, TimeUnit.SECONDS)    // socket timeout
        val dispatcher = Dispatcher()
        dispatcher.maxRequestsPerHost = 15
        builder.dispatcher(dispatcher)
        val factory = OkHttpUrlLoader.Factory(builder.build())
        registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}