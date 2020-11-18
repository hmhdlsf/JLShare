package com.julive.lib.helper

/**
 * Created by lsf on 2020/11/6 3:53 PM
 * Function : 工具类
 */
object ResHelper {
    fun forceCast(obj: Any?): Any? {
        return forceCast(obj, null)
    }

    fun forceCast(obj: Any?, defValue: Any?): Any? {
        if (obj == null) {
            return defValue
        } else {
            if (obj is Byte) {
                val value = obj
                if (defValue is Boolean) {
                    return value.toInt() != 0
                }
                if (defValue is Short) {
                    return value.toShort()
                }
                if (defValue is Char) {
                    return value.toChar()
                }
                if (defValue is Int) {
                    return Integer.valueOf(value.toInt())
                }
                if (defValue is Float) {
                    return value.toFloat()
                }
                if (defValue is Long) {
                    return value.toLong()
                }
                if (defValue is Double) {
                    return value.toDouble()
                }
            } else if (obj is Char) {
                val value = obj
                if (defValue is Byte) {
                    return value.toByte()
                }
                if (defValue is Boolean) {
                    return value.toInt() != 0
                }
                if (defValue is Short) {
                    return value.toShort()
                }
                if (defValue is Int) {
                    return Integer.valueOf(value.toInt())
                }
                if (defValue is Float) {
                    return value.toFloat()
                }
                if (defValue is Long) {
                    return value.toLong()
                }
                if (defValue is Double) {
                    return value.toDouble()
                }
            } else if (obj is Short) {
                val value = obj
                if (defValue is Byte) {
                    return value.toByte()
                }
                if (defValue is Boolean) {
                    return value.toInt() != 0
                }
                if (defValue is Char) {
                    return value.toChar()
                }
                if (defValue is Int) {
                    return Integer.valueOf(value.toInt())
                }
                if (defValue is Float) {
                    return value.toFloat()
                }
                if (defValue is Long) {
                    return value.toLong()
                }
                if (defValue is Double) {
                    return value.toDouble()
                }
            } else if (obj is Int) {
                val value = obj
                if (defValue is Byte) {
                    return value.toByte()
                }
                if (defValue is Boolean) {
                    return value != 0
                }
                if (defValue is Char) {
                    return value.toChar()
                }
                if (defValue is Short) {
                    return value.toShort()
                }
                if (defValue is Float) {
                    return value.toFloat()
                }
                if (defValue is Long) {
                    return value.toLong()
                }
                if (defValue is Double) {
                    return value.toDouble()
                }
            } else if (obj is Float) {
                val value = obj
                if (defValue is Byte) {
                    return value.toInt().toByte()
                }
                if (defValue is Boolean) {
                    return value != 0.0f
                }
                if (defValue is Char) {
                    return value.toInt().toChar()
                }
                if (defValue is Short) {
                    return value.toInt().toShort()
                }
                if (defValue is Int) {
                    return value.toInt()
                }
                if (defValue is Long) {
                    return value.toLong()
                }
                if (defValue is Double) {
                    return value.toDouble()
                }
            } else if (obj is Long) {
                val value = obj
                if (defValue is Byte) {
                    return value.toInt().toByte()
                }
                if (defValue is Boolean) {
                    return value != 0L
                }
                if (defValue is Char) {
                    return value.toInt().toChar()
                }
                if (defValue is Short) {
                    return value.toInt().toShort()
                }
                if (defValue is Int) {
                    return value.toInt()
                }
                if (defValue is Float) {
                    return value.toFloat()
                }
                if (defValue is Double) {
                    return value.toDouble()
                }
            } else if (obj is Double) {
                val value = obj
                if (defValue is Byte) {
                    return value.toInt().toByte()
                }
                if (defValue is Boolean) {
                    return value != 0.0
                }
                if (defValue is Char) {
                    return value.toInt().toChar()
                }
                if (defValue is Short) {
                    return value.toInt().toShort()
                }
                if (defValue is Int) {
                    return value.toInt()
                }
                if (defValue is Float) {
                    return value.toFloat()
                }
                if (defValue is Long) {
                    return value.toLong()
                }
            }
            try {
                return obj
            } catch (var4: Throwable) {
                return defValue
            }
        }
    }
}