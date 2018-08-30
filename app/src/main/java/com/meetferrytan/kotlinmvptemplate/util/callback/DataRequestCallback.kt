package com.meetferrytan.kotlinmvptemplate.util.callback

/**
 * Created by ferrytan on 10/22/17.
 */

interface DataRequestCallback<R> {
    fun onRequestSuccess(result: R)
}