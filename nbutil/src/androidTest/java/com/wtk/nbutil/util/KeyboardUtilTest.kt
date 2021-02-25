package com.wtk.nbutil.util

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

/**
 * author: WentaoKing
 * created on: 1/20/21
 * description:键盘工具类测试
 */

@RunWith(AndroidJUnit4::class)
class KeyboardUtilTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun showKeyboard(){
        val textView = TextView(appContext)
        KeyboardUtil.showKeyboard(textView)
    }
}