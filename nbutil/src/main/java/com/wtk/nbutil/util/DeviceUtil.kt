package com.wtk.nbutil.util

import android.content.Context
import android.text.TextUtils
import java.net.NetworkInterface
import java.util.*

/**
 * author: WentaoKing
 * created on: 1/25/21
 * description: 设备工具类
 */
object DeviceUtil {

    private const val NETWORK_ETHERNET = "eth0"
    private const val NETWORK_WIFI = "wlan0"

    /**
     * 获取设备的mac地址
     */
    fun getDeviceMac(context: Context): String {
        var deviceMac: String = getMACAddress(NETWORK_ETHERNET)
        // Get ethernet mac address
        if (!TextUtils.isEmpty(deviceMac)) {
            return deviceMac
        }

        // Get wifi mac address
        deviceMac = getMACAddress(NETWORK_WIFI)
        if (!TextUtils.isEmpty(deviceMac)) {
            return deviceMac
        }

        // Get other mac address
        deviceMac = getMACAddress(null)
        return if (!TextUtils.isEmpty(deviceMac)) {
            deviceMac
        } else ""
    }

    private fun getMACAddress(interfaceName: String?): String {
        try {
            val interfaces = Collections.list(
                    NetworkInterface.getNetworkInterfaces())
            for (netInterface in interfaces) {
                if (interfaceName != null) {
                    if (!interfaceName.equals(netInterface.name, ignoreCase = true)) {
                        continue
                    }
                }
                val mac = netInterface.hardwareAddress ?: return ""
                val buf = StringBuilder()
                for (idx in mac.indices)
                    buf.append(String.format("%02X:", mac[idx]))
                if (buf.isNotEmpty()) buf.deleteCharAt(buf.length - 1)
                return buf.toString()
            }
        } catch (ex: Exception) {
            // for now eat exceptions
        }
        return ""
    }

}