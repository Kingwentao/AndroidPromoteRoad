package com.wtk.nbutil.util

import android.Manifest
import android.app.Activity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * author: WentaoKing
 * created on: 1/22/21
 * description: 权限申请工具测试
 */
@RunWith(AndroidJUnit4::class)
class PermissionUtilTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val storagePermission = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val audioPermission = arrayOf(
            Manifest.permission.RECORD_AUDIO)

    @Test
    fun getPermission(){
        val infos =  PermissionsUtil
                .getPermissionGroupInfos(storagePermission,appContext)
        Assert.assertTrue(infos.isNotEmpty())
        for (info in infos){
            println("info:${info.desc} ${info.label}")
        }
    }

}