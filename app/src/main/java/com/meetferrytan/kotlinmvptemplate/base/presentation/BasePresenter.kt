package com.meetferrytan.kotlinmvptemplate.base.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.meetferrytan.kotlinmvptemplate.util.schedulers.SchedulerTransformers
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.lang.ref.WeakReference

// TODO remove WeakReference implementation, use lifecycle instead (if exist! problem might occur if View is not LifecycleOwner (e.g: Service)
// TODO Add CompositeDisposable for uninterruptable requests (e.g: POST methods) and handle how the callback should be called after view is re-attached
//          -> there should be a singleton CompositeDisposable for these?
//          -> private CompositeDisposable should be only if the Disposable requests are tightly coupled with View reference
abstract class BasePresenter<V : BaseContract.View>(private val schedulerTransformers: SchedulerTransformers)
    : BaseContract.Presenter<V>, LifecycleObserver {
    private var viewRef: WeakReference<V>? = null // Use WeakReference so setting the presenter to null during onDestroy is not really required
    private val compositeDisposable by lazy { CompositeDisposable() }
    override val view: V?
        get() = viewRef?.get()

    override fun attachView(mvpView: V) {
        viewRef = WeakReference(mvpView)
        setupLifecycleAwareListener()
    }

    override fun detachView() {
        clearDisposable()
        viewRef?.clear()
        viewRef = null
    }

    /**
     * Method to simplify processing Maybe Request
     *
     * @param maybe               -> Maybe request to process.
     * @param processId           -> unique processId from child presenter
     * @param dataRequestCallback -> data request callback
     * @param <F>                 -> generic response object
    </F> */
    protected fun <F : Any> subscribeMaybeRequest(maybe: Maybe<F>, processId: Int, dataRequestCallback: (F) -> Unit) {
        maybe.compose(schedulerTransformers.applyMaybeIoSchedulers())
                .doOnSubscribe { showLoading(processId, true) }
                .doAfterTerminate { showLoading(processId, false) }
                .subscribeBy(
                        onSuccess = { dataRequestCallback(it) },
                        onError = { throwable -> showError(processId, throwable) },
                        onComplete = { Timber.d("no data to emit for %d", processId) }
                )
                .addTo(compositeDisposable)
    }

    /**
     * Method to simplify processing Single Request
     *
     * @param single              -> Single request to process.
     * @param processId           -> unique processId from child presenter
     * @param dataRequestCallback -> data request callback
     * @param <F>                 -> generic response object
    </F> */
    protected fun <F : Any> subscribeSingleRequest(single: Single<F>, processId: Int, dataRequestCallback: (F) -> Unit) {
        single.compose(schedulerTransformers.applySingleIoSchedulers())
                .doOnSubscribe { showLoading(processId, true) }
                .doAfterTerminate { showLoading(processId, false) }
                .subscribeBy(
                        onSuccess = { result -> dataRequestCallback(result) },
                        onError = { throwable -> showError(processId, throwable) }
                )
                .addTo(compositeDisposable)
    }

    /**
     * Method to simplify processing Flowable Request with completion callback
     *
     * @param flowable                 -> Single request to process.
     * @param processId                -> unique processId from child presenter
     * @param dataRequestCallback      -> data request callback
     * @param completedRequestCallback -> request completion callback
     * @param <F>                      -> generic response object
    </F> */
    protected fun <F : Any> subscribeFlowableRequest(flowable: Flowable<F>, processId: Int, dataRequestCallback: (F) -> Unit, completedRequestCallback: () -> Unit = {}) {
        flowable.compose(schedulerTransformers.applyFlowableIoSchedulers())
                .doOnSubscribe { showLoading(processId, true) }
                .doAfterNext { showLoading(processId, false) }
                .subscribeBy(
                        onNext = { dataRequestCallback(it) },
                        onError = { throwable -> showError(processId, throwable) },
                        onComplete = { completedRequestCallback() }
                )
                .addTo(compositeDisposable)
    }

    /**
     * Method to simplify processing Completable Request which only need to know request complete / error
     *
     * @param completable              -> Completable request to process.
     * @param processId                -> unique processId from child presenter
     * @param completedRequestCallback -> request completion callback
     */
    protected fun subscribeCompletableRequest(completable: Completable, processId: Int, completedRequestCallback: () -> Unit) {
        completable.compose(schedulerTransformers.applyCompletableIoSchedulers())
                .doOnSubscribe { showLoading(processId, true) }
                .doAfterTerminate { showLoading(processId, false) }
                .subscribeBy(
                        onComplete = { completedRequestCallback() },
                        onError = { throwable -> showError(processId, throwable) }
                )
                .addTo(compositeDisposable)
    }

    protected fun silentSubscribe(completable: Completable): Disposable =
            completable
                    .compose(schedulerTransformers.applyCompletableIoSchedulers())
                    .subscribeBy(
                            onComplete = { Timber.d("Completable silentSubscription complete") },
                            onError = {
                                Timber.e("Completable silentSubscription error")
                                Timber.e(it)
                            }
                    )


    protected fun <F : Any> silentSubscribe(singleSource: Single<F>): Disposable =
            singleSource
                    .compose(schedulerTransformers.applySingleIoSchedulers())
                    .subscribeBy(
                            onSuccess = { Timber.d("Single silentSubscription success with result $it") },
                            onError = {
                                Timber.e("Single silentSubscription error")
                                Timber.e(it)
                            }
                    )

    protected fun <F : Any> silentSubscribe(maybeSource: Maybe<F>): Disposable =
            maybeSource
                    .compose(schedulerTransformers.applyMaybeIoSchedulers())
                    .subscribeBy(
                            onSuccess = { Timber.d("Maybe silentSubscription success with result $it") },
                            onError = {
                                Timber.e("Maybe silentSubscription error")
                                Timber.e(it)
                            },
                            onComplete = { Timber.d("Maybe silentSubscription complete") }
                    )

    /**
     * clear disposables
     */
    protected fun clearDisposable() {
        this.compositeDisposable.clear()
    }

    protected fun showLoading(processCode: Int, show: Boolean) {
        view?.showLoading(processCode, show)
    }

    /**
     * Use this method to use our own custom exception with errorCode and Message
     *
     * @param processCode the process code
     * @param throwable   the exception
     */
    protected fun showError(processCode: Int, throwable: Throwable) {
        Timber.e(throwable)
        view?.showError(processCode, throwable)
    }

    /**
     * setup presenter to be aware of view's lifecycle
     */
    private fun setupLifecycleAwareListener() {
        try {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        } catch (cce: ClassCastException) {
            Timber.wtf("%s not implemented Lifecycle owner, skip observing lifecycle.", view.toString())
        }

    }

    /**
     * Lifecycle Methods.
     * Override these methods in child presenter class to let the presenter know the view lifecycle.
     * make sure to check Lifecycle.getCurrentState before accessing instance which can be destroyed / not yet created.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
    }
}