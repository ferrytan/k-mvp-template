package com.meetferrytan.kotlinmvptemplate.base.presentation


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.meetferrytan.kotlinmvptemplate.util.callback.CompletedRequestCallback
import com.meetferrytan.kotlinmvptemplate.util.callback.DataRequestCallback
import com.meetferrytan.kotlinmvptemplate.util.schedulers.SchedulerTransformers
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

// TODO remove WeakReference implementation, use lifecycle instead (if exist! problem might occur if View is not LifecycleOwner (e.g: Service)
// TODO Add CompositeDisposable for uninterruptable requests (e.g: POST methods) and handle how the callback should be called after view is re-attached
//          -> there should be a singleton CompositeDisposable for these?
//          -> private CompositeDisposable should be only if the Disposable requests are tightly coupled with View reference
abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V>, LifecycleObserver {
    private var viewRef: WeakReference<V>? = null // Use WeakReference so setting the presenter to null during onDestroy is not really required
    private val mCompositeDisposable = CompositeDisposable()
    @Inject
    lateinit var schedulerTransformers: SchedulerTransformers

    override val view: V?
        get() = viewRef?.get()

    override fun attachView(mvpView: V?) {
        viewRef = WeakReference<V>(mvpView)
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
    protected fun <F> subscribeMaybeRequest(maybe: Maybe<F>, processId: Int, dataRequestCallback: DataRequestCallback<F>) {
        addDisposable(maybe
                .compose(schedulerTransformers.applyMaybeIoSchedulers())
                .doOnSubscribe { showLoading(processId, true) }
                .doAfterTerminate { showLoading(processId, false) }
                .subscribe(
                        { result -> dataRequestCallback.onRequestSuccess(result) },
                        { throwable -> showError(processId, throwable) },
                        { Timber.d("no data to emit for %d", processId) }))
    }

    /**
     * Method to simplify processing Single Request
     *
     * @param single              -> Single request to process.
     * @param processId           -> unique processId from child presenter
     * @param dataRequestCallback -> data request callback
     * @param <F>                 -> generic response object
    </F> */
    protected fun <F> subscribeSingleRequest(single: Single<F>, processId: Int, dataRequestCallback: DataRequestCallback<F>) {
        addDisposable(single
                .compose(schedulerTransformers.applySingleIoSchedulers())
                .doOnSubscribe { showLoading(processId, true) }
                .doAfterTerminate { showLoading(processId, false) }
                .subscribe(
                        { result -> dataRequestCallback.onRequestSuccess(result) },
                        { throwable -> showError(processId, throwable) }))
    }

    /**
     * Method to simplify processing Flowable Request
     *
     * @param flowable            -> Flowable request to process.
     * @param processId           -> unique processId from child presenter
     * @param dataRequestCallback -> data request callback
     * @param <F>                 -> generic response object
    </F> */
    protected fun <F> subscribeFlowableRequest(flowable: Flowable<F>, processId: Int, dataRequestCallback: DataRequestCallback<F>) {
        subscribeFlowableRequest(flowable, processId, dataRequestCallback, null)
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
    protected fun <F> subscribeFlowableRequest(flowable: Flowable<F>, processId: Int, dataRequestCallback: DataRequestCallback<F>, completedRequestCallback: CompletedRequestCallback?) {
        addDisposable(flowable
                .compose(schedulerTransformers.applyFlowableIoSchedulers())
                .doOnSubscribe { showLoading(processId, true) }
                .doAfterNext { showLoading(processId, false) }
                .subscribe(
                        { result -> dataRequestCallback.onRequestSuccess(result) },
                        { throwable -> showError(processId, throwable) },
                        { completedRequestCallback?.onComplete() }))
    }

    /**
     * Method to simplify processing Completable Request which only need to know request complete / error
     *
     * @param completable              -> Completable request to process.
     * @param processId                -> unique processId from child presenter
     * @param completedRequestCallback -> request completion callback
     */
    protected fun subscribeCompletableRequest(completable: Completable, processId: Int, completedRequestCallback: CompletedRequestCallback) {
        addDisposable(completable
                .compose(schedulerTransformers.applyCompletableIoSchedulers())
                .doOnSubscribe { showLoading(processId, true) }
                .doAfterTerminate { showLoading(processId, false) }
                .subscribe(
                        { completedRequestCallback.onComplete() },
                        { throwable -> showError(processId, throwable) }))
    }

    /**
     * add disposable to container
     *
     * @param disposable disposable to add
     */
    fun addDisposable(disposable: Disposable) {
        this.mCompositeDisposable.add(disposable)
    }

    /**
     * clear disposables
     */
    fun clearDisposable() {
        this.mCompositeDisposable.clear()
    }

    /**
     * setup presenter to be aware of view's lifecycle
     */
    private fun setupLifecycleAwareListener() {
        try {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        } catch (cce: ClassCastException) {
            Timber.wtf("%s not implemented Lifecycle owner, skip observing lifecycle.", view!!.toString())
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

    private fun showLoading(processCode: Int, show: Boolean) {
        view?.showLoading(processCode, show)
    }

    private fun showError(processCode: Int, throwable: Throwable) {
        view?.showError(processCode, throwable)
    }
}