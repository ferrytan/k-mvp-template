package com.meetferrytan.kotlinmvptemplate.util.glide

import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.BaseRequestOptions

/**
 * Created by ferrytan on 28/11/17.
 */

@GlideExtension
class MyGlideExtension private constructor() {

    companion object {
        @JvmStatic
        @GlideOption
        fun cacheResult(options: BaseRequestOptions<*>): BaseRequestOptions<*> =
                options.diskCacheStrategy(DiskCacheStrategy.DATA)

        @JvmStatic
        @GlideOption
        fun cacheSource(options: BaseRequestOptions<*>): BaseRequestOptions<*> =
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE)

        @JvmStatic
        @GlideOption
        fun cacheNothing(options: BaseRequestOptions<*>): BaseRequestOptions<*> =
                options.diskCacheStrategy(DiskCacheStrategy.NONE)
    }
}