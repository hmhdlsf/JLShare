package com.julive.sharelogin

import android.content.Context
import android.widget.Toast
import com.julive.lib.entity.OAuthUserInfo
import com.julive.lib.listener.LoginListener

open class WXLoginListener(private val context: Context) : LoginListener() {
    override fun onReceiveToken(
        accessToken: String,
        userId: String,
        expiresIn: Long,
        data: String?
    ) {
        super.onReceiveToken(accessToken, userId, expiresIn, data)
        val result = "登录成功"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

    override fun onReceiveUserInfo(userInfo: OAuthUserInfo) {
        val info = """ nickname = ${userInfo.nickName}
                       sex = ${userInfo.sex}
                       id = ${userInfo.userId}"""

        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    override fun onCancel() {
        super.onCancel()
        val result = "取消登录"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

    override fun onError(msg: String) {
        super.onError(msg)
        val result = "登录失败,失败信息：$msg"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

}