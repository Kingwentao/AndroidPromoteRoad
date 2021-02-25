package com.wtk.nbutil.util

import android.annotation.SuppressLint

/**
 * author: WentaoKing
 * created on: 1/22/21
 * description: 系统属性工具类
 */
object SystemPropertyUtil {

    @SuppressLint("PrivateApi")
    fun getInt(key: String, def: Int): Int {
        return try {
            val clazz = Class.forName("android.os.SystemProperties")
            val getMethod = clazz.getMethod("getInt", String::class.java, Int::class.java)
            getMethod.isAccessible = true
            getMethod.invoke(null, key, def) as Int
        } catch (e: Exception) {
            def
        }
    }

    @SuppressLint("PrivateApi")
    fun get(key: String, def: String): String {
        return try {
            val clazz = Class.forName("android.os.SystemProperties")
            val getMethod = clazz.getMethod("get", String::class.java, String::class.java)
            getMethod.isAccessible = true
            getMethod.invoke(null, key, def) as String
        } catch (e: Exception) {
            def
        }
    }

    @SuppressLint("PrivateApi")
    fun set(key: String, value: String) {
        try {
            val clazz = Class.forName("android.os.SystemProperties")
            val setMethod = clazz.getMethod("set", String::class.java, String::class.java)
            setMethod.isAccessible = true
            setMethod.invoke(null, key, value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("PrivateApi")
    fun setBoolean(key: String, value: Boolean) {
        set(key, value.toString())
    }

    @SuppressLint("PrivateApi")
    fun getBoolean(key: String, def: Boolean): Boolean {
        return try {
            val clazz = Class.forName("android.os.SystemProperties")
            val getMethod = clazz.getMethod("getBoolean", String::class.java, Boolean::class.java)
            getMethod.isAccessible = true
            getMethod.invoke(null, key, def) as Boolean
        } catch (e: Exception) {
            def
        }
    }
}