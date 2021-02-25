package com.wtk.nbutil.util

import org.junit.Assert
import org.junit.Test

/**
 * author: WentaoKing
 * created on: 1/25/21
 * description: 媒体工具类测试
 */

class MediaUtilTest {

    @Test
    fun validateMicValidate(){
        val isValidate = MediaUtil.validateMicAvailability()
        Assert.assertTrue(isValidate)
    }

}