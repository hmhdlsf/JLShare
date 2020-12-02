package com.julive.lib.helper

/**
 * Created by lsf on 2020/11/6 3:53 PM
 * Function : 工具类
 */
object ResHelper {
    fun forceCast(obj: Any?, defValue: Any? = null): Any? {
        if (obj == null) {
            return defValue
        } else {
            if (obj is Byte) {
                if (defValue is Boolean) {
                    return obj.toInt() != 0
                }
                if (defValue is Short) {
                    return obj.toShort()
                }
                if (defValue is Char) {
                    return obj.toChar()
                }
                if (defValue is Int) {
                    return Integer.valueOf(obj.toInt())
                }
                if (defValue is Float) {
                    return obj.toFloat()
                }
                if (defValue is Long) {
                    return obj.toLong()
                }
                if (defValue is Double) {
                    return obj.toDouble()
                }
            } else if (obj is Char) {
                if (defValue is Byte) {
                    return obj.toByte()
                }
                if (defValue is Boolean) {
                    return obj.toInt() != 0
                }
                if (defValue is Short) {
                    return obj.toShort()
                }
                if (defValue is Int) {
                    return Integer.valueOf(obj.toInt())
                }
                if (defValue is Float) {
                    return obj.toFloat()
                }
                if (defValue is Long) {
                    return obj.toLong()
                }
                if (defValue is Double) {
                    return obj.toDouble()
                }
            } else if (obj is Short) {
                if (defValue is Byte) {
                    return obj.toByte()
                }
                if (defValue is Boolean) {
                    return obj.toInt() != 0
                }
                if (defValue is Char) {
                    return obj.toChar()
                }
                if (defValue is Int) {
                    return Integer.valueOf(obj.toInt())
                }
                if (defValue is Float) {
                    return obj.toFloat()
                }
                if (defValue is Long) {
                    return obj.toLong()
                }
                if (defValue is Double) {
                    return obj.toDouble()
                }
            } else if (obj is Int) {
                if (defValue is Byte) {
                    return obj.toByte()
                }
                if (defValue is Boolean) {
                    return obj != 0
                }
                if (defValue is Char) {
                    return obj.toChar()
                }
                if (defValue is Short) {
                    return obj.toShort()
                }
                if (defValue is Float) {
                    return obj.toFloat()
                }
                if (defValue is Long) {
                    return obj.toLong()
                }
                if (defValue is Double) {
                    return obj.toDouble()
                }
            } else if (obj is Float) {
                if (defValue is Byte) {
                    return obj.toInt().toByte()
                }
                if (defValue is Boolean) {
                    return obj != 0.0f
                }
                if (defValue is Char) {
                    return obj.toInt().toChar()
                }
                if (defValue is Short) {
                    return obj.toInt().toShort()
                }
                if (defValue is Int) {
                    return obj.toInt()
                }
                if (defValue is Long) {
                    return obj.toLong()
                }
                if (defValue is Double) {
                    return obj.toDouble()
                }
            } else if (obj is Long) {
                if (defValue is Byte) {
                    return obj.toInt().toByte()
                }
                if (defValue is Boolean) {
                    return obj != 0L
                }
                if (defValue is Char) {
                    return obj.toInt().toChar()
                }
                if (defValue is Short) {
                    return obj.toInt().toShort()
                }
                if (defValue is Int) {
                    return obj.toInt()
                }
                if (defValue is Float) {
                    return obj.toFloat()
                }
                if (defValue is Double) {
                    return obj.toDouble()
                }
            } else if (obj is Double) {
                if (defValue is Byte) {
                    return obj.toInt().toByte()
                }
                if (defValue is Boolean) {
                    return obj != 0.0
                }
                if (defValue is Char) {
                    return obj.toInt().toChar()
                }
                if (defValue is Short) {
                    return obj.toInt().toShort()
                }
                if (defValue is Int) {
                    return obj.toInt()
                }
                if (defValue is Float) {
                    return obj.toFloat()
                }
                if (defValue is Long) {
                    return obj.toLong()
                }
            }
            return try {
                obj
            } catch (var4: Throwable) {
                defValue
            }
        }
    }
}