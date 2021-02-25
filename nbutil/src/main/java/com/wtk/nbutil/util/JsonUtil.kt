package com.wtk.nbutil.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type

/**
 * author: linrunyu
 * created on: 2020/3/25
 * description: Json工具类
 */
object JsonUtil {

    private val mGson = Gson()

    fun serialize(value: Any?): String? {
        return mGson.toJson(value)
    }

    @Throws(JsonSyntaxException::class)
    fun <T> deserialize(json: String?, clz: Class<T>?): T {
        return mGson.fromJson(json, clz)
    }

    @Throws(JsonSyntaxException::class)
    fun <T> deserialize(json: JsonObject?, clz: Class<T>?): T {
        return mGson.fromJson(json, clz)
    }

    @Throws(JsonSyntaxException::class)
    fun <T> deserialize(json: String?, type: Type?): T {
        return mGson.fromJson(json, type)
    }

}