package com.julive.lib.entity

import android.graphics.Bitmap
import android.view.View

/**
 * Created by lsf on 2020/11/13 3:28 PM
 * Function : 自定义平台信息
 */
class CustomPlatform(
    val label: String = "",
    val bitmap: Bitmap? = null,
    val listener: View.OnClickListener? = null
)