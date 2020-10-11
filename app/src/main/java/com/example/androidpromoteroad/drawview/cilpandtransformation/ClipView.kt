package com.example.androidpromoteroad.drawview.cilpandtransformation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.BitmapUtils
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 2020/10/11
 * description: 裁剪view示例代码
 */
class ClipView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    val IMAGE_SIZE = 300F.dp2px
    val IMAGE_PADDING = 20F.dp2px

    private val bitmap = BitmapUtils.getBitmap(resources, R.drawable.avatar, IMAGE_SIZE.toInt())
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipPath = Path().apply {
        addOval(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_SIZE,
            IMAGE_PADDING + IMAGE_SIZE,
            Path.Direction.CCW
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //注意：需先裁，再绘制，否则无效
        //clipPath clip后是会出现锯齿的，这是它的特性，不是bug，如果想不带锯齿效果，正确姿势要用Xfermode
        canvas?.clipPath(clipPath)
        canvas?.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, mPaint)
    }
}