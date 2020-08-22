package com.example.androidpromoteroad.drawview.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: WentaoKing
 * created on: 2020/8/22
 * description: xfermode 的使用
 */

private val IMAGE_WIDTH = 200f.dp2px
private val IMAGE_PADDING = 20f.dp2px
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class XfermodeView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectBounds = RectF(
        IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH,
        IMAGE_PADDING + IMAGE_WIDTH
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //准备一个离屏缓冲（区域尽可能的小，比较耗费性能）,不使用离屏缓冲，直接绘制就没有效果：画板的背景会影响模式的使用
        val count = canvas.saveLayer(rectBounds, null)
        canvas.drawOval(rectBounds, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getImageBitmap(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        paint.xfermode = null
        //释放离屏缓冲
        canvas.restoreToCount(count)
    }

    fun getImageBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
    }

}