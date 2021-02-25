package com.wtk.nbutil.util

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test

/**
 * author: WentaoKing
 * created on: 1/20/21
 * description: 网络工具类测试
 */
class NetworkUtilTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testConnect() {
        val networkState = NetworkUtil.getNetWorkStates(appContext)
        Assert.assertTrue(networkState == NetworkUtil.TYPE_NETWORK ||
                networkState == NetworkUtil.TYPE_NO_NETWORK)
        val isConnect = NetworkUtil.isNetworkConnect(appContext)
        println("isConnect: $isConnect")
    }

}