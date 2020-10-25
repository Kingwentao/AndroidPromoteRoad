package com.example.androidpromoteroad.drawview.drawableandbitmap

import android.graphics.*
import android.graphics.drawable.Drawable
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 2020/10/25
 * description: 自定义带网眼的drawable： 自定义drawable很重要的作用是：复用绘制的代码
 */
class MeshDrawable : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        strokeWidth = 5f.dp2px
    }

    private val meshInterval = 50f.dp2px

    override fun draw(canvas: Canvas) {
        var x = bounds.left.toFloat()
        while (x <= bounds.right) {
            canvas.drawLine(x, bounds.top.toFloat(), x, bounds.bottom.toFloat(), paint)
            x += meshInterval
        }
        var y = bounds.top.toFloat()
        while (y <= bounds.bottom) {
            canvas.drawLine(bounds.left.toFloat(), y, bounds.right.toFloat(), y, paint)
            y += meshInterval
        }
    }

    override fun setAlpha(p0: Int) {
        paint.alpha = p0
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun getOpacity(): Int {
        //根据透明度设置不透明度(简化版)
        return when (paint.alpha) {
            0 -> PixelFormat.TRANSPARENT
            0xff -> PixelFormat.OPAQUE
            else -> PixelFormat.TRANSLUCENT
        }
    }

    override fun setColorFilter(p0: ColorFilter?) {
        paint.colorFilter = p0
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }
}