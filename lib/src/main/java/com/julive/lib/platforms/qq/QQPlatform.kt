package com.julive.lib.platforms.qq

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.julive.lib.content.ShareContent
import com.julive.lib.content.ShareContentType
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.interfaces.IPlatform
import com.julive.lib.listener.ShareListener
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import java.util.*

/**
 * Created by lsf on 2020/11/5 4:04 PM
 * Function : QQ平台
 */
class QQPlatform : IPlatform {

    companion object {
        const val QQ_PACKAGE_NAME = "com.tencent.mobileqq"
        const val QQ_APP_ID = "qq_app_id"

        // QQ 好友
        const val FRIEND = "QQ_FRIEND"

        // QQ 空间
        const val ZONE = "qq_zone"
    }

    private var mUIListener: IUiListener? = null

    override fun getSupportTypes(): MutableList<String> {
        return mutableListOf(FRIEND, ZONE)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun isInstalled(context: Context): Boolean {
        val pm = context.applicationContext.packageManager ?: return false
        val packages = pm.getInstalledPackages(0)
        for (info in packages) {
            val name = info.packageName.toLowerCase(Locale.ENGLISH)
            if (QQ_PACKAGE_NAME == name) {
                return true
            }
        }
        return false
    }

    override fun doShare(
        activity: Activity?,
        shareType: String?,
        shareContent: ShareContent?,
        listener: ShareListener
    ) {
        mUIListener = object : QQHelper.QQUiListener(listener) {
            override fun onComplete(o: Any?) {
                listener.onSuccess()
            }
        }
        if (activity == null || shareContent == null || shareType == null) {
            return
        }
        val ten = getTencent(activity)

        when (shareType) {
            FRIEND -> {
                ten.shareToQQ(activity, QQHelper.qqFriendBundle(shareContent), mUIListener)
            }

            else -> {
                // 因为空间不支持分享单个文字和图片，在这里对于单个图片做了额外的处理，让其走发布说说的api
                if (shareContent.type == ShareContentType.TEXT || shareContent.type == ShareContentType.IMG) {
                    ten.publishToQzone(
                        activity,
                        QQHelper.publishToQzoneBundle(shareContent),
                        mUIListener
                    )
                } else {
                    ten.shareToQzone(
                        activity,
                        QQHelper.zoneBundle(shareContent),
                        mUIListener
                    )
                }
            }
        }
    }

    /**
     * 传入应用程序的全局context，可通过activity的getApplicationContext方法获取
     */
    fun getTencent(context: Context): Tencent {
        return Tencent.createInstance(
            ShareLoginHelper.getValue(context, QQ_APP_ID),
            context.applicationContext
        )
    }
}