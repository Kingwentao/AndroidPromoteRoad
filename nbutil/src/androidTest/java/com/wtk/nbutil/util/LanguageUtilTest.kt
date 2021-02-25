package com.wtk.nbutil.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * author: WentaoKing
 * created on: 1/20/21
 * description: 语言工具类测试
 */
@RunWith(AndroidJUnit4::class)
class LanguageUtilTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testLanguage() {
        val language = LanguageUtil.getLanguage(appContext)
        println("app: $language")
        Assert.assertEquals("zh_CN", language)

        val systemLanguage  = LanguageUtil.getSystemLocale().language
        println("system: $systemLanguage")
        Assert.assertEquals("zh_CN", systemLanguage)
    }

    @Test
    fun testModifyLanguage(){
        val oldLanguage = LanguageUtil.getLanguage(appContext)
        LanguageUtil.setLanguage(appContext, Locale("en"))
        val newLanguage = LanguageUtil.getLanguage(appContext)
        println(newLanguage)
        Assert.assertNotEquals(oldLanguage, newLanguage)
    }

}