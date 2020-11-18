package com.julive.lib.util

import android.app.Application
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Environment
import android.util.Log
import androidx.annotation.RestrictTo
import com.julive.lib.content.ShareContent
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.interfaces.IPlatform
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by lsf on 2020/11/9 2:20 PM
 * Function : share & login 工具类
 */
object SlUtils {

    private const val TAG = "ShareLoginLib"

    fun printLog(message: String) {
        if (ShareLoginHelper.DEBUG) {
            Log.i(TAG, "======> $message")
        }
    }

    fun printErr(message: String) {
        if (ShareLoginHelper.DEBUG) {
            Log.e(TAG, "======>$message")
        }
    }

    /**
     * 得到缓存图片的本地目录
     */
    fun generateTempPicDir(application: Application): String? {
        var tempPicDir: String? = null
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            try {
                tempPicDir = application.externalCacheDir.toString() + File.separator
                val dir = File(tempPicDir)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                tempPicDir = null
            }
        }
        return tempPicDir
    }

    /**
     * 将bitmap缩小到可以分享的大小，并变为byte数组
     *
     * Note:外部传入的bitmap可能会被用于其他的地方，所以这里不能做recycle()
     *
     * https://juejin.im/post/5b0bad475188251545422199
     * https://juejin.im/post/5b1a6b035188257d7102591a
     */
    fun getImageThumbByteArr(src: Bitmap?): ByteArray? {
        if (src == null) {
            return null
        }
        val WIDTH = 500
        val HEIGHT = 500
        val SIZE = '耀'.toLong() // 最大的图片大小
        val bitmap: Bitmap
        if (src.width > WIDTH || src.height > WIDTH) {
            // 裁剪为正方形的图片
            bitmap = ThumbnailUtils.extractThumbnail(src, WIDTH, HEIGHT)
            printLog("预览图过大，进行了裁剪")
        } else {
            bitmap = src
        }
        val outputStream =
            ByteArrayOutputStream(bitmap.width * bitmap.height)
        var options = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, outputStream)
        if (outputStream.size() > SIZE) {
            printLog("裁剪后的预览图仍旧过大，需要进一步压缩")
        }
        while (outputStream.size() > SIZE && options > 6) {
            outputStream.reset()
            options -= 6 // 不断的压缩图片的质量
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, outputStream)
        }
        printLog("最终的预览图大小：" + outputStream.size() + " ,目标大小：" + SIZE)

//        bitmap.recycle();
        return outputStream.toByteArray()
    }

    /**
     * 将比特率存入本地磁盤
     */
    fun saveBytesToFile(bytes: ByteArray?, picPath: String?): String? {
        if (bytes == null) {
            return null
        }
        try {
            FileOutputStream(picPath).use { fos ->
                fos.write(bytes)
                fos.close()
                return picPath
            }
        } catch (e: IOException) {
            e.printStackTrace()
            printErr("save thumb picture error")
            return null
        }
    }

    /**
     * 此方法是耗时操作，如果对于特别大的图，那么需要做异步
     *
     * Note:外部传入的bitmap可能会被用于其他的地方，所以这里不能做recycle()
     */
    fun saveBitmapToFile(bitmap: Bitmap?, imagePath: String?): String? {
        if (bitmap == null) {
            printErr("bitmap is null")
            return null
        }
        try {
            FileOutputStream(imagePath).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                return imagePath
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            printErr("save bitmap picture error")
            return null
        }
    }

    fun createPlatform(platformClz: Class<out IPlatform>): IPlatform {
        try {
            return platformClz.newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        throw RuntimeException("platform create error")
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    interface Function<T> {
        fun apply(content: ShareContent): T
    }
}