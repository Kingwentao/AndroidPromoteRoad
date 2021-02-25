package com.wtk.nbutil.util

import com.cvte.dss.business.PlatformUtil
import org.junit.Test

/**
 * author: WentaoKing
 * created on: 1/22/21
 * description: 平台工具测试类
 */
class PlatformUtilTest {

    @Test
    fun testCurPlatform(){
        val isEnableDRM4K = PlatformUtil.isEnableDRM4K()
        println("isEnable: $isEnableDRM4K")

        val isNeedEnableDRM4K = PlatformUtil.isNeedEnableDRM4K()
        println("isNeed: $isNeedEnableDRM4K")

        val icName = PlatformUtil.getIcName()
        println("icName: $icName")

        val flavorName = PlatformUtil.getFlavorName()
        println("flavor name: $flavorName")

        val isBoardPlatform = PlatformUtil.isBoardPlatform()
        println("isBoardPlatform: $isBoardPlatform")

        val enableThinPen = PlatformUtil.enableThinPen()
        println("enableThinPen: $enableThinPen")

        val isEnableFb = PlatformUtil.enableFBRender()
        println("isEnableFb: $isEnableFb")
    }
}