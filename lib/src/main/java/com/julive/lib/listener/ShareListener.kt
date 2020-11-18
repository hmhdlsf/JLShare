package com.julive.lib.listener

import androidx.annotation.CallSuper
import com.julive.lib.interfaces.IBaseListener
import com.julive.lib.util.SlUtils

/**
 * Created by lsf on 2020/11/9 3:41 PM
 * Function : 分享监听器
 */
open class ShareListener : IBaseListener {
    @CallSuper
    open fun onSuccess() {
        SlUtils.printLog("share success")
        onComplete()
    }
}