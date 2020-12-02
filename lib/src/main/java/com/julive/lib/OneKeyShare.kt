package com.julive.lib

import android.graphics.Bitmap
import android.view.View
import androidx.fragment.app.FragmentManager
import com.julive.lib.OneKeyShareHelper.getCustomPlatforms
import com.julive.lib.OneKeyShareHelper.getHiddenPlatform
import com.julive.lib.entity.CustomPlatform
import com.julive.lib.interfaces.IPlatformClickListener
import com.julive.lib.listener.ShareListener
import com.julive.lib.util.BottomSheetUtil
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

/**
 * Created by lsf on 2020/11/6 4:02 PM
 * Function : 一键分享
 */
class OneKeyShare {
    companion object {
        const val CUSTOM_PLATFORM = "custom.platform"
        const val HIDDEN_PLATFORMS = "hidden.platforms"
        const val CUSTOMIZE_CALL_BACK = "customize.call.back"
        const val SHARE_LISTENER = "share.listener"
    }

    private var params: HashMap<String, Any> = HashMap()

    init {
        params[CUSTOM_PLATFORM] = ArrayList<CustomPlatform>()
        params[HIDDEN_PLATFORMS] = ArrayList<String>()
    }

    /**
     * 设置平台点击的回调
     */
    fun setShareContentCustomizeCallback(callback: IPlatformClickListener) {
        params[CUSTOMIZE_CALL_BACK] = callback
    }

    /**
     * 设置分享回调
     */
    fun setShareCallback(callback: ShareListener) {
        params[SHARE_LISTENER] = callback
    }

    /**
     * 隐藏的平台
     */
    fun addHiddenPlatform(platform: String) {
        getHiddenPlatform(params).add(platform)
    }

    /**
     * 自定义平台
     */
    fun addCustomPlatform(label: String, bitmap: Bitmap, listener: View.OnClickListener) {
        val platform = CustomPlatform(label, bitmap, listener)
        val customPlatforms: ArrayList<CustomPlatform> = getCustomPlatforms(params)
        customPlatforms.add(platform)
    }

    /**
     * 展示分享弹窗
     */
    fun show(fragmentManager: FragmentManager) {
        BottomSheetUtil.showShare(fragmentManager, params)
    }

}