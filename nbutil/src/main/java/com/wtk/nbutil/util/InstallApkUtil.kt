package com.wtk.nbutil.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * author: WentaoKing
 * created on: 2020-03-11
 * description: 安装apk的工具类
 */
object InstallApkUtil {

    private const val TAG = "InstallUtil"

    /**
     * 安装apk方法（支持android 9）
     **/
    fun installApkInP(context: Context, packageManager: PackageManager,
                      apkFilePath: String, intent: Intent): String {

        val packageInstaller = packageManager.packageInstaller
        val packageArchiveInfo = packageManager.getPackageArchiveInfo(apkFilePath, 0)
        val pkgName = packageArchiveInfo?.packageName
                ?: throw Exception("packName is not found")

        val params = PackageInstaller
                .SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
        params.setAppPackageName(pkgName)

        try {
            //创建一个sessionId
            val sessionId = createSession(packageInstaller, params)
            Log.d(TAG, "sessionId = $sessionId")
            //根据sessionId打开一个session
            val session = packageInstaller.openSession(sessionId)
            val output = session.openWrite(pkgName, 0, -1)
            val input = FileInputStream(File(apkFilePath))
            val buffer = ByteArray(1024)
            var len = input.read(buffer)
            while (len != -1) {
                output.write(buffer, 0, len)
                len = input.read(buffer)
            }
            session.fsync(output)
            output.close()
            input.close()
            val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    sessionId,
                    intent,
                    0)
            session.commit(pendingIntent.intentSender)
            return pkgName
        } catch (e: Exception) {
            throw java.lang.Exception(e)
        }
    }

    /**
     * 根据 sessionParams 创建 Session
     **/
    private fun createSession(
            packageInstaller: PackageInstaller,
            sessionParams: PackageInstaller.SessionParams
    ): Int {
        val sessionId: Int
        try {
            sessionId = packageInstaller.createSession(sessionParams)
        } catch (e: IOException) {
            throw Exception("create session is failed!")
        }
        return sessionId
    }

}