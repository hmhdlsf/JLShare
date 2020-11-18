package com.julive.lib.listener

import android.content.Context
import android.widget.Toast
import com.julive.lib.listener.ShareListener

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

    override fun onError(msg: String) {
        super.onError(msg)
        val result = "分享失败，出错信息：$msg"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

}