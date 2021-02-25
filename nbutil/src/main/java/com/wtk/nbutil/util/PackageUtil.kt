package com.wtk.nbutil.util

import android.content.Context
import android.os.Build

/**
 * author: WentaoKing
 * created on: 1/25/21
 * description: 包工具类
 */
object PackageUtil {

    /**
     * 获取当前版本的code
     * @param context Context 应用context
     * @return Long 版本code
     */
    fun getCurVersionCode(context: Context): Long {
        val packInfo = context.packageManager?.getPackageInfo(context.packageName, 0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packInfo?.longVersionCode!!
        } else {
            packInfo?.versionCode!!.toLong()
        }
    }

}