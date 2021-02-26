package com.example.androidpromoteroad.bitmap

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.androidpromoteroad.R

/**
 * author: WentaoKing
 * created on: 2/25/21
 * description: Bitmap
 */
class BitmapMain {

    //bitmap复用方法
    fun bitmapReuse(res: Resources) {
        val oldOption = BitmapFactory.Options()
        oldOption.inMutable = true
        val bitmap = BitmapFactory.decodeResource(res, R.drawable.avatar, oldOption)

        val newOption = BitmapFactory.Options()
        newOption.inMutable = true
        newOption.inBitmap = bitmap
        val newBitmap = BitmapFactory.decodeResource(res, R.drawable.avatar, newOption)
    }


}