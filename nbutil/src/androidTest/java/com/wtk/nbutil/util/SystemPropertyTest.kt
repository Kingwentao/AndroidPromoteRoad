package com.wtk.nbutil.util

import org.junit.Assert
import org.junit.Test

/**
 * author: WentaoKing
 * created on: 1/22/21
 * description: 系统属性工具类测试
 */
class SystemPropertyTest {

    private val propertyName = "persist.qrcode.lan.mode"
    private val normalKey = "111"
    private val normalValue = "abc"
    private val intKey = "age"
    private val intValue = "2021"

    @Test
    fun setSystemProperty() {
        //set
        SystemPropertyUtil.set(normalKey,normalValue)
        //set boolean
        SystemPropertyUtil.setBoolean(propertyName, true)
        //set
        SystemPropertyUtil.set(intKey,intValue)
    }

    @Test
    fun getSystemProperty() {
        //get boolean
        val res = SystemPropertyUtil.getBoolean(propertyName, false)
        println("res: $res")
        //get
        val stringRes = SystemPropertyUtil.get(normalKey,"")
        println("string res: $stringRes")
        Assert.assertEquals(normalValue,stringRes)
        //get int
        val intRes = SystemPropertyUtil.getInt(intKey,0)
        println("int res: $intRes")
        Assert.assertEquals(intValue,intRes.toString())
    }

}