package com.julive.lib.util

import androidx.fragment.app.FragmentManager
import com.julive.lib.view.fragment.BottomShareFragment

/**
 * Created by lsf on 2020/11/10 4:01 PM
 * Function : 底部弹窗跳转工具类
 */
object BottomSheetUtil {
    fun showShare(fragmentManager: FragmentManager, map : HashMap<String, Any>) {
        val dialog = BottomShareFragment(map)
        dialog.show(fragmentManager, "Dialog")
    }
}