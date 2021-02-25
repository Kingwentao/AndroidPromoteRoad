package com.wtk.nbutil.util

import android.Manifest
import android.content.Intent
import android.content.pm.PackageInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * author: WentaoKing
 * created on: 1/25/21
 * description: 安装apk工具类测试
 */
@RunWith(AndroidJUnit4::class)
class InstallApkTest {

    private val apkPath = "/storage/emulated/0/test.apk"
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val mRuntimePermissionRule =
            GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE)

    @Test
    fun installApk() {
        val pm = appContext.packageManager
        var res: String = ""
        var packageArchiveInfo: PackageInfo? = null
        if (File(apkPath).exists()) {
            packageArchiveInfo = pm.getPackageArchiveInfo(apkPath, 0)
            println("pm: $pm package info: $packageArchiveInfo")
            res = InstallApkUtil.installApkInP(appContext, pm, apkPath, Intent())
        } else {
            println("file is not exist!")
        }
        Assert.assertTrue(res == packageArchiveInfo?.packageName)
    }

}