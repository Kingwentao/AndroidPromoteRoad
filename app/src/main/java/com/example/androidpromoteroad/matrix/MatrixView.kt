package com.example.androidpromoteroad.matrix

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.dp2px
import com.wtk.nbutil.util.BitmapUtil

/**
 * author: WentaoKing
 * created on: 8/14/21
 * description:
 */

class MatrixView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        private const val TAG = "MatrixView"
    }

    private val IMAGE_WIDTH = 250f.dp2px
    private val IMAGE_PADDING = 20f.dp2px
    private val paint = Paint()
    private var mBitmap =
        BitmapUtil.getImageBitmap(resources, R.drawable.avatar, IMAGE_WIDTH.toInt())

    private val mMatrix = Matrix()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mMatrix.reset()
        mMatrix.apply {
            postTranslate(
                (width / 2 - mBitmap.width / 2).toFloat(),
                (height / 2 - mBitmap.height / 2).toFloat()
            )
            Log.d(TAG, "1: ${toShortString()} width / 2: ${width / 2} height / 2: ${height / 2}")
            postScale(0.5f, 0.5f, (width / 2).toFloat(), (height / 2).toFloat())
            Log.d(TAG, "2: ${toShortString()}")
        }
        canvas.drawBitmap(mBitmap, mMatrix, paint)
    }

}