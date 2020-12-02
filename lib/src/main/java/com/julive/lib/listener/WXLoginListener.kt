package com.julive.lib.listener

import android.content.Context
import android.widget.Toast
import com.julive.lib.entity.OAuthUserInfo

open class WXLoginListener(private val context: Context) : LoginListener() {
    override fun onReceiveToken(
            accessToken: String,
            uId: String,
            expiresIn: Long,
            wholeData: String?
    ) {
        super.onReceiveToken(accessToken, uId, expiresIn, wholeData)
        val result = "登录成功"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

    override fun onReceiveUserInfo(userInfo: OAuthUserInfo) {
        val info = """ nickname = ${userInfo.nickName}
                       sex = ${userInfo.sex}
                       id = ${userInfo.userId}"""

        Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
    }

    override fun onCancel() {
        super.onCancel()
        val result = "取消登录"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

    override fun onError(errorMsg: String) {
        super.onError(errorMsg)
        val result = "登录失败,失败信息：$errorMsg"
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }

}