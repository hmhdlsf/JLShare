package com.julive.lib.interfaces

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.julive.lib.content.ShareContent
import com.julive.lib.content.ShareContentType
import com.julive.lib.listener.LoginListener
import com.julive.lib.listener.ShareListener

/**
 * Created by lsf on 2020/11/5 3:52 PM
 * Function : 定义平台的接口
 */
interface IPlatform {

    /**
     * 该平台支持的操作类型
     */
    fun getSupportTypes(): MutableList<String> {
        return mutableListOf()
    }

    /**
     * 是否安装该平台App
     */
    fun isInstalled(context: Context): Boolean {
        return false
    }

    /**
     * 检查当前环境，如果异常则直接终止
     *
     * @param type             当前平台支持的操作类型
     * @param shareContentType 分享时传入的分享类型，如果是登录则会传[ShareContent.NO_CONTENT]
     */
    fun checkEnvironment(
        context: Context?,
        type: String?,
        @ShareContentType shareContentType: Int?
    ) {
    }

    /**
     * 调起登录
     */
    fun doLogin(
        activity: Activity,
        listener: LoginListener
    ) {}

    /**
     * 调起分享
     */
    fun doShare(
        activity: Activity,
        shareType: String?,
        shareContent: ShareContent?,
        listener: ShareListener
    ) {
    }

    /**
     * 调起短信分享
     */
    fun doShortMsg(activity: Activity, phone: String?, msg: String?) {}

    /**
     * 处理响应的结果
     */
    fun onResponse(activity: Activity, intent: Intent) {}
}