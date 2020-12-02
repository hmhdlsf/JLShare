package com.julive.lib.http

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object HttpUtil {
    @JvmStatic
    fun sendHttpRequest(address: String?, listener: HttpCallBackListener?) {
        Thread(
                Runnable {
                    try {
                        val url = URL(address)
                        val connection = url.openConnection() as HttpURLConnection
                        connection.requestMethod = "GET"
                        connection.connectTimeout = 8000
                        connection.readTimeout = 8000
                        val `in` = connection.inputStream
                        val reader =
                                BufferedReader(InputStreamReader(`in`))
                        val response = StringBuilder()
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            response.append(line)
                        }
                        listener?.onFinish(response.toString())
                    } catch (e: MalformedURLException) {
                        listener?.onError(e)
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
        ).start()
    }
}