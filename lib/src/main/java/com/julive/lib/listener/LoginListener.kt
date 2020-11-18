package com.julive.lib.listener

import androidx.annotation.CallSuper
import com.julive.lib.entity.OAuthUserInfo
import com.julive.lib.interfaces.IBaseListener
import com.julive.lib.util.SlUtils

/**
 * Created by lsf on 2020/11/9 3:42 PM
 * Function : 登录监听器
 */
open class LoginListener : IBaseListener {
    /**
     * @param accessToken 第三方给的一次性token，几分钟内会失效
     * @param uId         用户的id
     * @param expiresIn   过期时间
     * @param wholeData   第三方本身返回的全部数据
     */
    @CallSuper
    open fun onReceiveToken(
        accessToken: String,
        uId: String,
        expiresIn: Long,
        wholeData: String?
    ) {
        SlUtils.printLog("login success \naccessToken = $accessToken\nuserId = $uId\nexpires_in = $expiresIn")
    }

    /**
     * 得到第三方平台的用户信息
     *
     * 本库希望不要获取太多的用户信息，故{OAuthUserInfo}仅提供基础的信息，如果不满足请请提交{issue}
     */
    open fun onReceiveUserInfo(userInfo: OAuthUserInfo) {
        SlUtils.printLog(
            """
                nickname = ${userInfo.nickName.toString()}
                sex = ${userInfo.sex.toString()}
                id = ${userInfo.userId}
                """.trimIndent()
        )
        onComplete()
    }
}