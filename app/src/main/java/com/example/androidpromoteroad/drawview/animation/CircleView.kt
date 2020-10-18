package com.example.androidpromoteroad.drawview.animation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.utils.dp2px
import kotlin.math.cos
import kotlin.math.sin

/**
 * author: created by wentaoKing
 * date: created in 2020/20/18
 * description: 圆形View
 */
class CircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var mRadius = 50f.dp2px
        set(value) {
            field = value
            invalidate()
        }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 15f.dp2px
        color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(width / 2f, height / 2f, mRadius, paint)
    }

}