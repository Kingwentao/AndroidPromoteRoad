package com.wtk.nbutil.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * author: WentaoKing
 * created on: 1/20/21
 * description: 网络工具类
 */
object NetworkUtil {

    /**
     * 网络状态改变ACTION
     */
    const val NET_CONN_STATUS_CHANGED_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

    const val TYPE_NO_NETWORK = -1
    const val TYPE_NETWORK = 0

    /**
     * 获取网络状态
     */
    fun getNetWorkStates(context: Context): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetWorkInfo = connectivityManager.activeNetworkInfo
        return if (activeNetWorkInfo == null || !activeNetWorkInfo.isConnected) {
            TYPE_NO_NETWORK
        } else {
            TYPE_NETWORK
        }
    }

    /**
     * 是否有网络连接
     * @param context Context
     * @return Boolean
     */
    fun isNetworkConnect(context: Context): Boolean {
        return getNetWorkStates(context) == TYPE_NETWORK
    }

}