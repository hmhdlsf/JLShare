package com.julive.lib.extensions

/**
 * Created by lsf on 2020/11/13 4:20 PM
 * Function : map的拓展函数集合
 */
fun Map<Any, Any>?.isValid(): Boolean {
    return this != null && isNotEmpty()
}