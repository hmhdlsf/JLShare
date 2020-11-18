package com.julive.lib.view.entity

import android.graphics.Bitmap
import android.view.View

/**
 * Created by lsf on 2020/11/10 3:33 PM
 * Function : 分享布局实体类
 */
class ShareGridEntity {
    var platformLabel: String = ""
    var platformImg: Bitmap? = null
    var platformName: String = ""
    var listener: View.OnClickListener ?= null
}