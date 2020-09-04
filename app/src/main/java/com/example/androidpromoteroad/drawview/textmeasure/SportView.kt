package com.example.androidpromoteroad.drawview.textmeasure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 2020/9/1
 * description: by sport view to lean text measure
 */

private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
private val RING_WIDTH = 20.dp2px
private val RADIUS = 150.dp2px


class SportView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100.dp2px
        typeface = ResourcesCompat.getFont(context, R.font.font)
        textAlign = Paint.Align.CENTER
        //isFakeBoldText 假的粗体，它是描出来的
        //isFakeBoldText = true
    }
    private val fontMetrics = Paint.FontMetrics()
    private val bounds = Rect()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        // 绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS, height / 2f - RADIUS,
            width / 2f + RADIUS, height / 2f + RADIUS,
            -90f, 225f, false, paint
        )

        //文字绘制的难点：纵向居中
        //绘制静态文字适合用： getTextBounds 测量实际边界
        val text1 = "bbbb"
        paint.getTextBounds(text1, 0, text1.length, bounds)
        //因为文字是以baseLine为垂直基准线，所以需要计算垂直偏移量，并减去它
        println("height: ${height/2}")
        println("bound.top: ${bounds.top}")
        println("bound.bottom: ${bounds.bottom}")
        canvas.drawRect(bounds,paint)
        canvas.drawText(text1, width /2f ,height/2f - (bounds.top + bounds.bottom)/2f, paint)

        // 绘制文字
        //绘制动态文字适合用： getFontMetrics 字体的方式，(避免不同文字差异过大导致的跳动)
        // 用ascent,descent边界线算出文本的偏移量
//        paint.textSize = 100.dp2px
//        paint.style = Paint.Style.FILL
//        paint.getFontMetrics(fontMetrics)
//        canvas.drawText(
//            "ppab", width / 2f,
//            height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f, paint
//        )
    }

}