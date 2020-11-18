package com.julive.lib.platforms.wx

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.util.SlUtils

class EventHandlerActivity : Activity() {

    companion object {
        const val KEY_REQUEST_CODE = "share_login_lib_key_request_code"
        const val KEY_RESULT_CODE = "share_login_lib_key_result_code"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 为了防止这个activity关不掉，这里给用户一个点击关闭的功能
        findViewById<View>(R.id.content).setOnClickListener { v: View? -> finish() }
        if (savedInstanceState != null) {
            SlUtils.printLog("EventHandlerActivity:onCreate(2) intent:" + intent.extras)
            handleResp(intent)
        } else {
            ShareLoginHelper.onActivityCreate(this)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        SlUtils.printLog("EventHandlerActivity:onNewIntent() intent:" + intent.extras)
        handleResp(intent)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent
    ) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (intent != null) {
            intent.putExtra(KEY_REQUEST_CODE, requestCode)
            intent.putExtra(KEY_RESULT_CODE, resultCode)
            SlUtils.printLog("EventHandlerActivity:onActivityResult() intent:" + intent.extras)
        } else {
            SlUtils.printErr("EventHandlerActivity:onActivityResult() intent is null")
        }
        handleResp(intent)
        finish()
    }

    private fun handleResp(data: Intent) {
        if (ShareLoginHelper.getCurPlatform() != null) {
            ShareLoginHelper.getCurPlatform()?.onResponse(this, data)
        } else {
            SlUtils.printErr("ShareLoginLib.curPlatform is null")
        }
    }

    override fun onResume() {
        super.onResume()
        SlUtils.printLog("EventHandlerActivity:onResume()")
    }

    override fun onPause() {
        super.onPause()
        SlUtils.printLog("EventHandlerActivity:onPause()")
    }

    override fun onStop() {
        super.onStop()
        SlUtils.printLog("EventHandlerActivity:onStop()")
    }

    override fun onDestroy() {
        SlUtils.printLog("EventHandlerActivity:onDestroy()")
        ShareLoginHelper.destroy()
        super.onDestroy()
    }

    interface OnCreateListener {
        fun onCreate(activity: EventHandlerActivity?)
    }
}