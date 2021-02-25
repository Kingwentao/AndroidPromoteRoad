package com.wtk.nbutil.util

import android.content.Context
import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cvte.dss.extension.dp2px
import com.cvte.dss.extension.dp2pxInt
import com.cvte.dss.extension.px2dp
import com.cvte.dss.extension.px2dpInt
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * author: WentaoKing
 * created on: 1/20/21
 * description: 显示工具类测试
 */

@RunWith(AndroidJUnit4::class)
class DisplayUtilTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun dp2px() {
        val dpValue = 6.67f
        val px1 = dp2pxFloat(appContext, dpValue)
        val px2 = dpValue.dp2px()
        println("px: $px1  px2: $px2")
        assertEquals(px1, px2)

        val px11 = dp2px(appContext, dpValue)
        val px22 = dpValue.dp2pxInt()
        println("px: $px11  px2: $px22")
        assertEquals(px11, px22)

        val dpIntValue = 6
        val px3 = dp2pxFloat(appContext, dpIntValue.toFloat())
        val px4 = dpIntValue.dp2px()
        println("px3: $px3  px4: $px4")
        assertEquals(px3, px4)

        val px33 = dp2px(appContext, dpIntValue.toFloat())
        val px44 = dpIntValue.dp2pxInt()
        println("px33: $px33  px44: $px44")
        assertEquals(px3, px4)
    }

    @Test
    fun px2dp() {
        val pxFloatValue = 6.5f
        val dp1 = px2dp(appContext, pxFloatValue)
        val dp2 = pxFloatValue.px2dp()
        println("dp1: $dp1 dp2: $dp2")
        assertEquals(dp1, dp2)

        val dp11 = px2dp(appContext.resources, pxFloatValue)
        val dp22 = pxFloatValue.px2dpInt()
        println("dp11: $dp11 dp22: $dp22")
        assertEquals(dp11, dp22)

        val pxIntValue = 6
        val dp3 = px2dp(appContext, pxIntValue.toFloat())
        val dp4 = pxIntValue.px2dp()
        println("dp3: $dp3  dp4: $dp4")
        assertEquals(dp3, dp4)

        val dp33 = px2dp(appContext.resources, pxIntValue.toFloat())
        val dp44 = pxIntValue.px2dpInt()
        println("dp3: $dp33  dp4: $dp44")
        assertEquals(dp33, dp44)
    }

    @Test
    fun testScreenSize() {
        val scWidth = DisplayUtil.getScreenWidth(appContext)
        val scHeight = DisplayUtil.getScreenHeight(appContext)
        assertTrue(scWidth > 0)
        assertTrue(scHeight > 0)
    }

    //以下为测试期望值
    fun px2dp(context: Context, value: Float): Float {
        val density = context.resources.displayMetrics.density
        return value / density
    }

    fun px2dp(resources: Resources, value: Float): Int {
        val density = resources.displayMetrics.density
        return (value / density + 0.5f).toInt()
    }

    fun dp2px(context: Context, value: Float): Int {
        val density = context.resources.displayMetrics.density
        return (value * density + 0.5f).toInt()
    }

    fun dp2px(resources: Resources, value: Float): Int {
        val density = resources.displayMetrics.density
        return (value * density + 0.5f).toInt()
    }

    fun dp2pxFloat(context: Context, value: Float): Float {
        val density = context.resources.displayMetrics.density
        return value * density
    }

}