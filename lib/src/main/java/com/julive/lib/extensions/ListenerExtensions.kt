package com.julive.lib.extensions

import android.view.View

/**
 * Created by lsf on 2020/11/13 4:31 PM
 * Function : listener 拓展函数集合
 */
fun View.OnClickListener?.isValid(): Boolean {
    return this != null
}