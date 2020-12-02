package com.julive.lib.util

import android.util.Log
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.interfaces.IPlatform
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

}