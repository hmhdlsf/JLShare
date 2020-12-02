package com.julive.lib.platforms.wx

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.julive.lib.entity.OAuthUserInfo
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.http.HttpCallBackListener
import com.julive.lib.http.HttpUtil
import com.julive.lib.listener.LoginListener
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by lsf on 2020/11/17 3:06 PM
 * Function : 微信登录帮助类
 */
object WXLoginHelper {

    /**
     * BaseResp的getType函数获得的返回值
     * 1：第三方授权；2：分享
     */
    private const val TYPE_LOGIN = 1

    /**
     * 解析用户登录的结果
     */
    fun parseLoginResp(
        activity: Activity,
        baseResp: BaseResp,
        listener: LoginListener
    ) {
        if (baseResp is SendAuth.Resp && baseResp.getType() == TYPE_LOGIN) {
            when (baseResp.errCode) {
                BaseResp.ErrCode.ERR_OK -> code2Token(
                    activity,
                    baseResp.code,
                    listener
                ) // 通过code换取token
                BaseResp.ErrCode.ERR_USER_CANCEL -> listener.onCancel()
                BaseResp.ErrCode.ERR_AUTH_DENIED -> listener.onError("用户拒绝授权")
                else -> listener.onError("未知错误，错误码：" + baseResp.errCode)
            }
        }
    }

    /**
     * 返回结果：
     * {
     * "access_token":"ACCESS_TOKEN", // token
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE",
     * "unionid":"o6_bmajkjhjhhkj"
     * }
     */
    private fun code2Token(
        context: Context,
        code: String,
        listener: LoginListener
    ) {
        val appId: String? = ShareLoginHelper.getValue(context, WXPlatform.WX_APP_ID)
        val secret: String? = ShareLoginHelper.getValue(context, WXPlatform.WX_APP_SECRET)
        val url = ("https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid=" + appId
                + "&secret=" + secret
                + "&code=" + code
                + "&grant_type=authorization_code")
        HttpUtil.sendHttpRequest(url, object : HttpCallBackListener {
            override fun onFinish(response: String?) {
                //解析以及存储获取到的信息
                try {
                    val jsonObject = JSONObject(response)
                    val accessToken = jsonObject.getString("access_token")
                    val openid = jsonObject.getString("openid")
                    val expiresIn = jsonObject.getLong("expires_in") // access_token接口调用凭证超时时间，单位（秒）
                    listener.onReceiveToken(accessToken, openid, expiresIn, jsonObject.toString())
                    getUserInfo(context, accessToken, openid, listener)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    listener.onError(e.message!!)
                }
            }

            override fun onError(e: Exception?) {
                Toast.makeText(context, "通过code获取数据没有成功", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * {
     * "openid":"OPENID",
     * "nickname":"NICKNAME",
     * "sex":1,
     * "province":"PROVINCE",
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl": "https://avatars3.githubusercontent.com/u/9552155?v=3&s=460",
     * "privilege":[
     * "PRIVILEGE1",
     * "PRIVILEGE2"
     * ],
     * "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     */
    fun getUserInfo(
        context: Context?,
        accessToken: String,
        uid: String,
        listener: LoginListener?
    ) {

        val url = ("https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessToken
                + "&openid="
                + uid)

        UserInfoHelper.getUserInfo(
            context,
            url,
            listener
        ) { jsonObj ->
            val userInfo = OAuthUserInfo()
            userInfo.nickName = jsonObj.getString("nickname")
            userInfo.sex = jsonObj.getString("sex")
            // 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
            userInfo.headImgUrl = jsonObj.getString("headimgurl")
            userInfo.userId = jsonObj.getString("unionid")
            userInfo
        }
    }
}