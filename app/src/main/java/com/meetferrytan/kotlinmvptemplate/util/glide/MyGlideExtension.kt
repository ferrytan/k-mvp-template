package com.meetferrytan.kotlinmvptemplate.util.glide

import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Created by ferrytan on 28/11/17.
 */

@GlideExtension
object MyGlideExtension {

    @GlideOption
    @JvmStatic
    fun cacheResult(options: RequestOptions): RequestOptions =
            options.diskCacheStrategy(DiskCacheStrategy.DATA)

    @GlideOption
    @JvmStatic
    fun cacheSource(options: RequestOptions): RequestOptions =
            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE)

    @GlideOption
    @JvmStatic
    fun cacheNothing(options: RequestOptions): RequestOptions =
            options.diskCacheStrategy(DiskCacheStrategy.NONE)
}