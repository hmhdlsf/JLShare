package com.julive.lib.http

interface HttpCallBackListener {
    fun onFinish(response: String?)
    fun onError(e: Exception?)
}