package com.example.utilslibrary

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * author: WentaoKing
 * created on: 2020/9/5
 * description: bitmap工具
 */
object BitmapUtil {

    fun getImageBitmap(resources: Resources, bitmap: Int, width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, bitmap, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, bitmap, options)
    }
}