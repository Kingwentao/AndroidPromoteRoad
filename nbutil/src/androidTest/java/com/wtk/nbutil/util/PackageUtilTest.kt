package com.wtk.nbutil.util

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test

/**
 * author: WentaoKing
 * created on: 1/25/21
 * description: 包工具类测试
 */
class PackageUtilTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testVersionCode() {
        val versionCode = PackageUtil.getCurVersionCode(appContext)
        println("code is : $versionCode")
        Assert.assertNotNull(versionCode)
    }

}