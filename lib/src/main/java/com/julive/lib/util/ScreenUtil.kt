package com.julive.lib.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by lsf on 2020/11/10 5:03 PM
 * Function : 屏幕工具类
 */
object ScreenUtil {

    @SuppressLint("WrongConstant")
    fun getScreenHeight(context: Context?): Int {
        val metric = DisplayMetrics()
        val wm = context?.getSystemService("window") as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }

    @SuppressLint("WrongConstant")
    fun getScreenWidth(context: Context?): Int {
        val metric = DisplayMetrics()
        val wm = context?.getSystemService("window") as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }
}