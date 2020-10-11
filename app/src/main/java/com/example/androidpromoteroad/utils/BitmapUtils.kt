package com.example.androidpromoteroad.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * author: created by wentaoKing
 * date: created in 2020/10/11
 * description:
 */
object BitmapUtils {

    fun getBitmap(resources: Resources, res: Int, width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, res, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, res, options)
    }
}