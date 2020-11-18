package com.julive.lib

import com.julive.lib.OneKeyShare.Companion.CUSTOMIZE_CALL_BACK
import com.julive.lib.OneKeyShare.Companion.SHARE_LISTENER
import com.julive.lib.entity.CustomPlatform
import com.julive.lib.helper.ResHelper
import com.julive.lib.interfaces.IPlatformClickListener
import com.julive.lib.listener.ShareListener
import java.util.*

/**
 * Created by lsf on 2020/11/13 3:51 PM
 * Function : 一键分享帮助类
 */
object OneKeyShareHelper {
    fun getCustomPlatforms(params: HashMap<String, Any>): ArrayList<CustomPlatform> {
        return ResHelper.forceCast(params[OneKeyShare.CUSTOM_PLATFORM]) as ArrayList<CustomPlatform>
    }

    fun getHiddenPlatform(params: HashMap<String, Any>): ArrayList<String> {
        return ResHelper.forceCast(params[OneKeyShare.HIDDEN_PLATFORMS]) as ArrayList<String>
    }

    fun getPlatformClickListener(params: HashMap<String, Any>): IPlatformClickListener {
        return ResHelper.forceCast(params[CUSTOMIZE_CALL_BACK]) as IPlatformClickListener
    }

    fun getShareListener(params: HashMap<String, Any>): ShareListener {
        return ResHelper.forceCast(params[SHARE_LISTENER]) as ShareListener
    }
}