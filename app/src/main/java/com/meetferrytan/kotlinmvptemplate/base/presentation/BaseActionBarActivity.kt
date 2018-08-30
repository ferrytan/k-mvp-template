package com.meetferrytan.kotlinmvptemplate.base.presentation

import android.view.MenuItem
import android.view.View
import android.view.WindowManager

/**
 * Created by ferrytan on 9/20/17.
 */

abstract class BaseActionBarActivity : BaseInjectionActivity() {
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setTitle(titleId: Int) {
        supportActionBar?.setTitle(titleId)
    }

    override fun setTitle(title: CharSequence) {
        supportActionBar?.title = title
    }

    protected fun showSystemUI(show: Boolean) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (show) {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            } else {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                        or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                        or View.SYSTEM_UI_FLAG_IMMERSIVE)
            }
        }
    }

    protected fun setWindowTouchEnabled(enabled: Boolean) {
        if (enabled) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

}