package com.julive.lib.content

import androidx.annotation.IntDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    ShareContentType.TEXT,
    ShareContentType.IMG,
    ShareContentType.MUSIC,
    ShareContentType.VIDEO,
    ShareContentType.WEB,
    ShareContentType.MINI_PROGRAM,
    ShareContentType.SHORT_MSG,
    ShareContentType.APP,
    ShareContentType.JSON
)
annotation class ShareContentType {
    companion object {
        const val TEXT = 1
        const val IMG = 2
        const val MUSIC = 3
        const val VIDEO = 4
        const val WEB = 5
        const val MINI_PROGRAM = 6
        const val SHORT_MSG = 7
        const val APP = 8
        const val JSON = 9
    }
}