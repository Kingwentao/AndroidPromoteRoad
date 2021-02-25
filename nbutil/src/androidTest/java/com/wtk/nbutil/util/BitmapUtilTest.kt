package com.wtk.nbutil.util

import android.Manifest
import android.graphics.Bitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * author: WentaoKing
 * created on: 1/23/21
 * description: bitmap工具类测试
 */
@RunWith(AndroidJUnit4::class)
class BitmapUtilTest {

    @get:Rule
    val mRuntimePermissionRule =
            GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val imagePath = "/storage/emulated/0/4.jpg"
    private val targetPath = "/storage/emulated/0/5.jpg"
    private val reqW = 200
    private val reqH = 200

    @Test
    fun saveBitmapToSdCard() {
        println("image path: $imagePath  ${File(imagePath).exists()}")
        //test right path
        val bitmap = BitmapUtil.decodeSampledBitmap(imagePath, reqW, reqH)
        println("bitmap: $bitmap")
        Assert.assertTrue(bitmap != null)
        //test save
        bitmap?.let {
            println("width: ${bitmap.width}")
            val saveRes = BitmapUtil.saveBitmapToImageFile(bitmap,
                    Bitmap.CompressFormat.JPEG, targetPath)
            println("saveRes: $saveRes")
            Assert.assertTrue(saveRes)
        }
    }

    @Test
    fun decodeBitmap() {
        //test null path
        val bitmap = BitmapUtil.decodeSampledBitmap("",
                reqW, reqH)
        println("bitmap: $bitmap")
        Assert.assertTrue(bitmap == null)
    }

    @Test
    fun scaleBitmap() {
        val bitmap = getBitmap()
        val scaleBitmap = BitmapUtil.scaleBitmap(bitmap!!, 100f, 100f)
        println("scale result: $scaleBitmap w: ${scaleBitmap?.width} h: ${scaleBitmap?.height} ")
        val whRes = scaleBitmap?.width == 100 && scaleBitmap?.height == 100
        Assert.assertTrue(scaleBitmap != null)
        Assert.assertTrue(whRes)
    }

    private fun getBitmap(): Bitmap? {
        return BitmapUtil.decodeSampledBitmap(imagePath, reqW, reqH)
    }

}