package com.wtk.nbutil.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * author: WentaoKing
 * created on: 1/25/21
 * description: 设备工具类测试
 */
@RunWith(AndroidJUnit4::class)
class DeviceUtilTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testMacAddress(){
        val address = DeviceUtil.getDeviceMac(appContext)
        println("address: $address")
        Assert.assertNotNull(address)
    }

}