package com.meetferrytan.kotlinmvptemplate.util.schedulers

import io.reactivex.*

/**
 * Created by ferrytan on 11/01/18.
 */

class SchedulerTransformers(val ioScheduler: Scheduler, val mainScheduler: Scheduler, val computationScheduler: Scheduler) {

    fun <T> applySingleIoSchedulers(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.subscribeOn(ioScheduler)
                    .observeOn(mainScheduler)
        }
    }

    fun <T> applySingleComputationSchedulers(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.subscribeOn(computationScheduler)
                    .observeOn(mainScheduler)
        }
    }

    fun <T> applyMaybeIoSchedulers(): MaybeTransformer<T, T> {
        return MaybeTransformer { upstream ->
            upstream.subscribeOn(ioScheduler)
                    .observeOn(mainScheduler)
        }
    }

    fun <T> applyFlowableIoSchedulers(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(ioScheduler)
                    .observeOn(mainScheduler)
        }
    }

    fun <T> applyFlowableComputationSchedulers(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(computationScheduler)
                    .observeOn(mainScheduler)
        }
    }

    fun applyCompletableIoSchedulers(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.subscribeOn(ioScheduler)
                    .observeOn(mainScheduler)
        }
    }
}