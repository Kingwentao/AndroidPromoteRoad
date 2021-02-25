package com.wtk.nbutil.util

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.junit.Test

/**
 * author: WentaoKing
 * created on: 1/22/21
 * description: json工具类测试
 */
class JsonUtilTest {

    data class TestObj(
            @SerializedName("name")
            val name: String,
            @SerializedName("age")
            val age: Int)


    val jsonString = "{\"name\":\"zhangsan\",\"age\":2021}"

    @Test
    fun deserialize() {
        val string1 = JsonUtil.deserialize(jsonString,TestObj::class.java)
        println("string1: $string1")

        val jsonObject = JsonObject()
        jsonObject.addProperty("name","zhangsan")
        jsonObject.addProperty("age","2021")
        val string2 = JsonUtil.deserialize(jsonObject,TestObj::class.java)
        println("string2: $string2")
    }

}