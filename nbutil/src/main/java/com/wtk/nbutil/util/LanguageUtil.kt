package com.wtk.nbutil.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import java.util.*

/**
 * author: WentaoKing
 * created on: 1/20/21
 * description: 语言工具类
 */
object LanguageUtil {

    /**
     * 获取应用当前语音，格式zh_CN、en
     */
    fun getLanguage(context: Context): String {
        return context.resources.configuration.locale.toString()
    }

    /**
     * 设置语言（仅用于应用内的资源）
     */
    fun setLanguage(context: Context, locale: Locale) {
        val contextWrapper = getContextWrapper(context, locale)
        val dm = context.resources.displayMetrics
        context.resources.updateConfiguration(contextWrapper.resources.configuration, dm)
    }

    /**
     * 设置语言（可用于应用外的资源）
     */
    fun setLanguage(context: Context, resource: Resources, locale: Locale) {
        val dm = context.resources.displayMetrics
        val configure = Configuration()
        configure.locale = locale
        resource.updateConfiguration(configure, dm)
    }

    private fun getContextWrapper(context: Context, locale: Locale): ContextWrapper {
        val res = context.resources
        val newContext: Context
        val configuration = res.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            newContext = context.createConfigurationContext(configuration)
        } else {
            configuration.setLocale(locale)
            newContext = context.createConfigurationContext(configuration)
        }
        return ContextWrapper(newContext)
    }

    /**
     * 获取系统的语言
     */
    fun getSystemLocale(): Locale {
        val locale: Locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0)
        } else {
            locale = Locale.getDefault()
        }
        return locale
    }

}