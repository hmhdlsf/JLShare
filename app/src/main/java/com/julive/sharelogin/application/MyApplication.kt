package com.julive.sharelogin.application

import android.app.Application
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.platforms.qq.QQPlatform
import com.julive.lib.platforms.wx.WXPlatform

/**
 * Created by lsf on 2020/11/5 5:02 PM
 * Function : 初始化
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化第三方平台的信息
        ShareLoginHelper.initPlatforms(
            listOf(
                QQPlatform::class.java,
                WXPlatform::class.java
            )
        )
    }
}