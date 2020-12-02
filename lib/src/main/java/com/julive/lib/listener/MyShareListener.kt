package com.julive.lib.listener

import android.content.Context
import android.widget.Toast

open class MyShareListener(private val context: Context) : ShareListener() {
    override fun onSuccess() {
        super.onSuccess()
        val result = "分享成功"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

    override fun onCancel() {
        super.onCancel()
        val result = "取消分享"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

    override fun onError(errorMsg: String) {
        super.onError(errorMsg)
        val result = "分享失败，出错信息：$errorMsg"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

}