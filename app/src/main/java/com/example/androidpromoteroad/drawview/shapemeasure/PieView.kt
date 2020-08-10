package com.example.androidpromoteroad.drawview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.utils.dp2px
import kotlin.math.cos
import kotlin.math.sin

/**
 * author: created by wentaoKing
 * date: created in 2020/8/9
 * description: 饼图View
 */
class PieView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
    private val COLORS = listOf(
        Color.parseColor("#C2185B"),
        Color.parseColor("#00ACC1"),
        Color.parseColor("#558B2F"),
        Color.parseColor("#5D4037")
    )

    companion object {
        //弧形开口角度
        private const val OPEN_ANGLE = 120
        private const val RADIUS = 150f

        //移动的饼号
        private const val TRANSLATE_NUM = 2

        //移动饼的偏移长度
        private const val TRANSLATE_LENGTH = 20f
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    }

    override fun onDraw(canvas: Canvas?) {
        //画弧
        val left = width / 2 - RADIUS.dp2px
        val top = height / 2 - RADIUS.dp2px
        val right = width / 2 + RADIUS.dp2px
        val bottom = height / 2 + RADIUS.dp2px
        var startAngle = 0f
        for ((index, angle) in ANGLES.withIndex()) {
            paint.color = COLORS[index]
            //偏移饼图，这里可以直接移动canvas来实现，比较简单
            if (index == TRANSLATE_NUM - 1) {
                canvas?.save()
                val tx =
                    TRANSLATE_LENGTH.dp2px * cos(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat()
                val ty =
                    TRANSLATE_LENGTH.dp2px * sin(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat()
                canvas?.translate(tx, ty)
            }
            //绘制弧形
            canvas?.drawArc(left, top, right, bottom, startAngle, angle, true, paint)
            startAngle += angle
            if (index == TRANSLATE_NUM - 1) {
                canvas?.restore()
            }
        }

    }

}