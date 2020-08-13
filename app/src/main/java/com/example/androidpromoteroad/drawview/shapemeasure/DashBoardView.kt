package com.example.androidpromoteroad.drawview.shapemeasure

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
 * description: 仪表盘View
 */
class DashBoardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        //弧形开口角度
        private const val OPEN_ANGLE = 120

        //虚线的宽度和长度
        private const val DASH_WIDTH = 3f
        private const val DASH_HEIGHT = 10f

        //指针长度
        private const val POINT_LENGTH = 250f
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    val dashPath = Path()
    val path = Path()
    var markNum: Int = 10

    private lateinit var pathEffect: PathDashPathEffect

    init {
        paint.strokeWidth = 3f.dp2px
        paint.style = Paint.Style.STROKE

        dashPaint.strokeWidth = 2f.dp2px
        dashPaint.style = Paint.Style.STROKE

        dashPath.addRect(0f, 0f, DASH_WIDTH.dp2px, DASH_HEIGHT.dp2px, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        val left = width / 2 - 150f.dp2px
        val top = height / 2 - 150f.dp2px
        val right = width / 2 + 150f.dp2px
        val bottom = height / 2 + 150f.dp2px
        val startAngle = 90 + OPEN_ANGLE / 2
        val sweepAngle = 360 - OPEN_ANGLE
        //添加弧形的path
        path.addArc(
            left, top, right, bottom, startAngle.toFloat(),
            sweepAngle.toFloat()
        )

        //准备绘制刻度的条件
        val pathMeasure = PathMeasure(path, false)
        pathEffect = PathDashPathEffect(
            dashPath, (pathMeasure.length - DASH_WIDTH) / 20, 0f,
            PathDashPathEffect.Style.ROTATE
        )
        dashPaint.pathEffect = pathEffect
    }

    override fun onDraw(canvas: Canvas?) {
        //画弧
        canvas?.drawPath(path, paint)
        //画刻度
        canvas?.drawPath(path, dashPaint)
        //画指针
        val startX = (width / 2).toFloat()
        val startY = (height / 2).toFloat()
        val stopX = width / 2 + POINT_LENGTH * cos(markToRadius()).toFloat()
        val stopY = height / 2 + POINT_LENGTH * sin(markToRadius()).toFloat()
        canvas?.drawLine(startX, startY, stopX, stopY, paint)
    }

    /**
     * 指针所在位置的角度
     * @param markNum
     */
    private fun markToRadius() =
        Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20f * markNum).toDouble())

}