package com.julive.lib.interfaces

import com.julive.lib.content.ShareContent
import com.julive.lib.entity.Platform

interface IPlatformClickListener {
    fun onPlatformClick(platform: Platform, shareContent: ShareContent)
}