package com.julive.lib.interfaces

import androidx.annotation.CallSuper
import com.julive.lib.util.SlUtils

interface IBaseListener {
    @CallSuper
    fun onError(errorMsg: String) {
        SlUtils.printErr("login or share error:$errorMsg")
        onComplete()
    }

    @CallSuper
    fun onCancel() {
        SlUtils.printLog("login or share canceled")
        onComplete()
    }

    fun onComplete() {}
}