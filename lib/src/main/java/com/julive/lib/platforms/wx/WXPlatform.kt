package com.julive.lib.platforms.wx

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.julive.lib.content.ShareContent
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.interfaces.IPlatform
import com.julive.lib.listener.LoginListener
import com.julive.lib.listener.ShareListener
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * Created by lsf on 2020/11/5 4:03 PM
 * Function : 微信平台
 */
class WXPlatform : IPlatform {

    companion object {
        const val WX_APP_ID = "WX_APP_ID"
        const val WX_APP_SECRET = "WX_APP_SECRET"

        // ---------------------------------------------------------------
        const val LOGIN = "weixin_login"

        // 好友
        const val FRIEND = "weixin_friend" + SendMessageToWX.Req.WXSceneSession

        // 朋友圈
        const val TIMELINE = "weixin_timeline" + SendMessageToWX.Req.WXSceneTimeline

        // 收藏
        const val FAVORITE = "weixin_favorite" + SendMessageToWX.Req.WXSceneFavorite
    }

    private var wxEventHandler: IWXAPIEventHandler? = null

    override fun getSupportTypes(): MutableList<String> {
        return mutableListOf(FRIEND, TIMELINE, FAVORITE)
    }

    override fun isInstalled(context: Context): Boolean {
        return getWXApi(context).isWXAppInstalled
    }

    override fun doLogin(
        activity: Activity,
        listener: LoginListener
    ) {
        val request = SendAuth.Req()
        request.scope = "snsapi_userinfo" // 期望得到用户信息


        sendRequest(activity, request, object : IWXAPIEventHandler {
            override fun onReq(baseReq: BaseReq) {}
            override fun onResp(baseResp: BaseResp) {
                WXLoginHelper.parseLoginResp(activity, baseResp, listener)
                activity.finish()
            }
        })
    }

    override fun doShare(
        activity: Activity,
        shareType: String?,
        shareContent: ShareContent?,
        listener: ShareListener
    ) {
        shareContent?.let { content ->
            shareType?.let { type ->
                val request: SendMessageToWX.Req = WXShareHelper.createRequest(content, type)

                sendRequest(activity, request, object : IWXAPIEventHandler {
                    override fun onReq(baseReq: BaseReq) {}
                    override fun onResp(baseResp: BaseResp) {
                        WXShareHelper.parseShareResp(baseResp, listener)
                        activity.finish()
                    }
                })
            }
        }
    }

    private fun sendRequest(
        activity: Activity,
        request: BaseReq,
        eventHandler: IWXAPIEventHandler
    ) {
        wxEventHandler = eventHandler

        val api: IWXAPI = getWXApi(activity)
        api.registerApp(ShareLoginHelper.getValue(activity, WX_APP_ID))
        api.sendReq(request) // 这里的请求的回调会在handlerResp中收到
    }

    override fun onResponse(activity: Activity, intent: Intent) {
        getWXApi(activity).handleIntent(intent, wxEventHandler)
    }

    fun getWXApi(context: Context): IWXAPI {
        return WXAPIFactory.createWXAPI(
            context.applicationContext,
            ShareLoginHelper.getValue(context, WX_APP_ID),
            true
        )
    }

}