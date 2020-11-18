package com.julive.lib.extensions

/**
 * Created by lsf on 2020/11/13 4:08 PM
 * Function : List 拓展函数集合
 */
fun List<Any>?.isValid(): Boolean {
    return this != null && isNotEmpty()
}