package com.julive.lib.helper

import android.R
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import com.julive.lib.content.ShareContent
import com.julive.lib.interfaces.IPlatform
import com.julive.lib.listener.LoginListener
import com.julive.lib.listener.ShareListener
import com.julive.lib.platforms.shortMsg.ShortMsgPlatform
import com.julive.lib.platforms.wx.EventHandlerActivity
import com.julive.lib.platforms.wx.EventHandlerActivity.OnCreateListener
import com.julive.lib.util.SlUtils
import com.tencent.mm.opensdk.utils.ILog
import com.tencent.mm.opensdk.utils.Log
import java.lang.ref.WeakReference
import java.util.*


/**
 * Created by lsf on 2020/11/5 5:12 PM
 * Function : 分享&登录 帮助类
 */
object ShareLoginHelper {

    var APP_NAME: String? = ""
    var DEBUG = false
    var TEMP_PIC_DIR: String? = ""

    private lateinit var mValueMap: Map<String, String>

    private lateinit var mSupportPlatforms: List<Class<out IPlatform>>

    private var onCreateListener: OnCreateListener? = null

    private var wrPlatform: WeakReference<IPlatform>? = null

    fun init(
        application: Application,
        curAppName: String?,
        tempPicDir: String?,
        debug: Boolean
    ) {
        APP_NAME = curAppName
        DEBUG = debug

        if (TextUtils.isEmpty(tempPicDir)) {
            TEMP_PIC_DIR = SlUtils.generateTempPicDir(application)
        }
        if (DEBUG) {
//            LogUtil.enableLog()
            Log.setLogImpl(null) // 如果为null则会用默认的log输出
        } else {
//            LogUtil.disableLog()
            Log.setLogImpl(object : ILog {
                override fun v(s: String, s1: String) {
                    // do nothing
                }

                override fun d(s: String, s1: String) {
                    // do nothing
                }

                override fun i(s: String, s1: String) {
                    // do nothing
                }

                override fun w(s: String, s1: String) {
                    // do nothing
                }

                override fun e(s: String, s1: String) {
                    // do nothing
                }
            })
        }
    }

    fun initPlatforms(
        platforms: List<Class<out IPlatform>>
    ) {
        mSupportPlatforms = platforms
    }

    fun getValue(context: Context, key: String): String? {
        var value: String? = ""
        try {
            val appInfo: ApplicationInfo = context.packageManager
                .getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                )
            value = appInfo.metaData.getString(key)
            if (value.isNullOrEmpty()) {
                value = appInfo.metaData.getInt(key).toString()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return value
    }

    fun doLogin(
        activity: Activity,
        type: String?,
        listener: LoginListener?
    ) {
        if (type == null) {
            listener?.onError("type is null")
            return
        }
        doAction(activity, true, type, null, listener, null)
    }

    fun doShare(
        activity: Activity,
        type: String?,
        shareContent: ShareContent?,
        listener: ShareListener?
    ) {
        if (type == null || shareContent == null) {
            listener?.onError("type or shareContent is null")
            return
        }
        doAction(activity, false, type, shareContent, null, listener)
    }

    private fun doAction(
        activity: Activity,
        isLoginAction: Boolean,
        type: String?,
        content: ShareContent?,
        loginListener: LoginListener?,
        shareListener: ShareListener?
    ) {

        // 1. 得到目前支持的平台列表
        var loginListener: LoginListener? = loginListener
        var shareListener: ShareListener? = shareListener
        val platforms = ArrayList<IPlatform>()
        if (mSupportPlatforms != null) {
            for (platformClz in mSupportPlatforms) {
                platforms.add(SlUtils.createPlatform(platformClz))
            }
        }

        // 2. 根据type匹配出一个目标平台
        var curPlatform: IPlatform? = null
        for (platform in platforms) {
            for (s in platform.getSupportTypes()) {
                if (s == type) {
                    curPlatform = platform
                    break
                }
            }
        }

        // 3. 初始化监听器
        if (loginListener == null) {
            loginListener = LoginListener()
        }
        if (shareListener == null) {
            shareListener = ShareListener()
        }

        // 4. 检测当前运行环境，看是否正常
        try {
            if (curPlatform == null) {
                throw UnsupportedOperationException("未找到支持该操作的平台，当前的操作类型为：$type")
            } else {
                curPlatform.checkEnvironment(
                    activity,
                    type,
                    content?.type
                )
            }
        } catch (throwable: Throwable) {
            if (isLoginAction) {
                throwable.message?.let { loginListener.onError(it) }
            } else {
                throwable.message?.let { shareListener.onError(it) }
            }
            return
        }

        // 5. 启动辅助的activity，最终执行具体的操作
        val finalLoginListener: LoginListener = loginListener
        val finalShareListener: ShareListener = shareListener
        val finalCurPlatform: IPlatform = curPlatform

        onCreateListener = object : OnCreateListener {
            override fun onCreate(activity: EventHandlerActivity?) {
                if (isLoginAction) {
                    finalCurPlatform.doLogin(activity, finalLoginListener)
                } else {
                    content?.apply {
                        finalCurPlatform.doShare(
                            activity,
                            type,
                            content,
                            finalShareListener
                        )
                    }
                }
            }
        }

        activity.startActivity(Intent(activity, EventHandlerActivity::class.java))
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        wrPlatform = WeakReference(curPlatform)
    }

    fun doShortMsg(activity: Activity, phone: String?, msg: String?) {
        val platform = ShortMsgPlatform()
        platform.doShortMsg(activity, phone, msg)
    }

    fun onActivityCreate(activity: EventHandlerActivity) {
        onCreateListener?.onCreate(activity)
    }

    fun getCurPlatform(): IPlatform? {
        return wrPlatform?.get()
    }

    fun destroy() {
        // 如果不clear，那么在不保留活动的时候则会收到回调
        // 但此时的回调已经没有意义，收到回调的activity已经被销毁，所以一般这里需要清理引用
        wrPlatform?.clear()
        onCreateListener = null
    }

}