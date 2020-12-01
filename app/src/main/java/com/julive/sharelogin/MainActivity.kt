package com.julive.sharelogin

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.julive.lib.entity.OAuthUserInfo
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.listener.WXLoginListener
import com.julive.lib.platforms.wx.WXPlatform

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()

        supportFragmentManager?.let { ShareUtil.doShare(this, it, ShareInfo()) }
        ShareLoginHelper.doLogin(this, WXPlatform.LOGIN, object : WXLoginListener(this) {
            override fun onReceiveToken(
                accessToken: String,
                userId: String,
                expiresIn: Long,
                data: String?
            ) {
                super.onReceiveToken(accessToken, userId, expiresIn, data)
            }

            override fun onReceiveUserInfo(userInfo: OAuthUserInfo) {
                super.onReceiveUserInfo(userInfo)
            }

            override fun onCancel() {
                super.onCancel()
            }

            override fun onError(msg: String) {
                super.onError(msg)
            }
        })
    }
}
