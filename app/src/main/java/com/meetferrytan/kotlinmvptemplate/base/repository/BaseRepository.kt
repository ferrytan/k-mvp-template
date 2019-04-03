package com.meetferrytan.kotlinmvptemplate.base.repository

import com.ciayo.comics.data.repository.base.Mapper
import com.meetferrytan.kotlinmvptemplate.util.schedulers.SchedulerTransformers
import io.reactivex.Single
import timber.log.Timber

abstract class BaseRepository<L : BaseDao<DATA>, R, DATA, APIRESPONSE>
constructor(val localSource: L, val remoteSource: R, val schedulerTransformers: SchedulerTransformers) {

    private val mapper: Mapper<APIRESPONSE, DATA> by lazy {
        setMapper()
    }

    abstract fun setMapper(): Mapper<APIRESPONSE, DATA>

    fun Single<List<APIRESPONSE>>.syncDataList(afterSyncAction: ((List<DATA>) -> Unit)? = null): Single<List<DATA>> = this
            .map { mapper.map(it) }
            .doOnSuccess {
                localSource.upsert(it)
                afterSyncAction?.invoke(it)
            }
            .doOnError { Timber.e(it)}
            .compose(schedulerTransformers.applySingleIoSchedulers())

    fun Single<APIRESPONSE>.syncData(afterSyncAction: ((DATA) -> Unit)? = null): Single<DATA> = this
            .map { mapper.map(it) }
            .doOnSuccess {
                localSource.upsert(it)
                afterSyncAction?.invoke(it)
            }
            .doOnError { Timber.e(it)}
            .compose(schedulerTransformers.applySingleIoSchedulers())
}