package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

abstract class BaseMvpInputActivity<P : BaseContract.Presenter<V>, V: BaseContract.View> : BaseMvpActivity<P, V>() {
    protected var isKeyboardShown: Boolean = false
        private set
    protected var keyboardHeight: Int = 0
        get() = if (isKeyboardShown) field else 0

    private var keyboardListenersAttached = false
    private lateinit var rootLayout: ViewGroup

    private val keyboardLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        // navigation bar height
        // display window size for the app layout
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)

        // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
        this.keyboardHeight = (rootLayout.rootView.height - (statusBarHeight + navBarHeight + rect.height().toFloat())).toInt()

        if (this.keyboardHeight <= 0) {
            if (isKeyboardShown) {
                isKeyboardShown = false
                onHideKeyboard()
            }
        } else {
            if (!isKeyboardShown) {
                onShowKeyboard(this.keyboardHeight)
                isKeyboardShown = true
            }
        }
    }

    protected abstract fun setRootView(): ViewGroup

    protected abstract fun onShowKeyboard(keyboardHeight: Int)

    protected abstract fun onHideKeyboard()

    private fun attachKeyboardListeners(root: ViewGroup) {
        if (keyboardListenersAttached) {
            return
        }
        rootLayout = root
        rootLayout.viewTreeObserver.addOnGlobalLayoutListener(keyboardLayoutListener)

        keyboardListenersAttached = true
    }

    protected fun showKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    protected fun forceShowKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }

    protected fun hideKeyBoard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    protected fun hideKeyBoard(activity: Activity) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var focusedView = activity.currentFocus
        if (focusedView == null) {
            focusedView = View(activity)
        }
        imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }

    protected override fun onResume() {
        super.onResume()
        attachKeyboardListeners(setRootView())
    }

    protected override fun onPause() {
        super.onPause()
        if (keyboardListenersAttached) {
            rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(keyboardLayoutListener)
            keyboardListenersAttached = false
        }
    }
}