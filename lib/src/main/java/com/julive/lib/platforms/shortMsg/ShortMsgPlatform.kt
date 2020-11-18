package com.julive.lib.platforms.shortMsg

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.julive.lib.interfaces.IPlatform

/**
 * Created by lsf on 2020/11/5 4:05 PM
 * Function : 短信平台
 */
class ShortMsgPlatform : IPlatform {
    override fun doShortMsg(activity: Activity, phone: String?, msg: String?) {
        val smsToUri = Uri.parse("smsto:")
        val sendIntent = Intent(Intent.ACTION_VIEW, smsToUri)
        // 手机号
        sendIntent.putExtra("address", phone)
        // 短信内容
        sendIntent.putExtra("sms_body", msg)
        sendIntent.type = "vnd.android-dir/mms-sms"
        activity.startActivityForResult(sendIntent, 1002)
    }
}