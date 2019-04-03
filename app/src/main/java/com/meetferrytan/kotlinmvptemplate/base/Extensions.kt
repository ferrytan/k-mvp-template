package com.meetferrytan.kotlinmvptemplate.base

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager


fun Context.getDisplayWidth(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}

fun Context.getDisplayHeight(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.y
}

fun Context.getStatusBarHeight(): Int {
    // todo check os here
    // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Context.getNavigationBarHeight(): Int {
    val navBarSize = 0
    try {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val realDisplayMetrics = DisplayMetrics()
            display.getMetrics(displayMetrics)
            display.getRealMetrics(realDisplayMetrics)
            if (displayMetrics.heightPixels != realDisplayMetrics.heightPixels) {
                return getNavigationBarSize()
            }
        } else {
            val resources = resources
            val resourceID = resources.getIdentifier("config_showNavigationBar", "bool", "android")
            if (resourceID > 0 && resources.getBoolean(resourceID))
                return getNavigationBarSize()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return navBarSize
}

fun Context.getNavigationBarSize(): Int {
    val resources = resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else 0
}