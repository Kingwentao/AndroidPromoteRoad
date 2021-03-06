package com.example.androidpromoteroad.drawview.textmeasure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
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

        //1.文字绘制的难点：纵向居中

        //1.1 绘制静态文字适合用： getTextBounds 测量实际边界
        val text1 = "bbbb"
        paint.getTextBounds(text1, 0, text1.length, bounds)
        //因为文字是以baseLine为垂直基准线，所以需要计算垂直偏移量，并减去它
        println("height: ${height / 2}")
        println("bound.top: ${bounds.top}")
        println("bound.bottom: ${bounds.bottom}")
        canvas.drawText(text1, width / 2f, height / 2f - (bounds.top + bounds.bottom) / 2f, paint)

        //1.2 绘制动态文字适合用： getFontMetrics 字体的方式获取范围 (避免不同文字差异过大导致的跳动)
        // 用ascent,descent边界线算出文本的偏移量
//        paint.textSize = 100.dp2px
//        paint.style = Paint.Style.FILL
//        paint.getFontMetrics(fontMetrics)
//        canvas.drawText(
//            "ppab", width / 2f,
//            height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f, paint
//        )


        //1.3 绘制贴边的文字
        //最好使用 bounds.top（而不是ascent） 进行偏移
        val text3 = "abab"
        paint.textSize = 150f.dp2px
        paint.textAlign = Paint.Align.LEFT
        paint.getFontMetrics(fontMetrics)
        paint.getTextBounds(text3, 0, text3.length, bounds)
        canvas.drawText(text3, 0F, -bounds.top.toFloat(), paint)

        //1.4 绘制多行文字
        val text4 =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin dignissim lacinia massa. " +
                    "Nam ac commodo lectus, a pretium tellus. Aenean lobortis semper nisl id scelerisque. " +
                    "Quisque non diam id nisl ullamcorper consectetur id et diam. Morbi a arcu sapien." +
                    " Vivamus laoreet metus sed sodales maximus. Donec accumsan pellentesque magna, " +
                    "quis dictum sem sagittis placerat. Praesent bibendum condimentum laoreet. " +
                    "Mauris pharetra libero rhoncus dignissim pretium. Maecenas laoreet massa non sapien dignissim," +
                    " in tincidunt nisl luctus. Pellentesque bibendum pulvinar nisi sit amet consequat."
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 16f.dp2px
            style = Paint.Style.STROKE
        }
        val stateLayout =
            StaticLayout(text4, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
        stateLayout.draw(canvas)

    }

}

/**
 * 文字共有5条线
 * top bottom 最大范围限制(因为有很多文字奇奇怪怪，高度可能很高)
 * ascent  descent 文字的核心的范围
 * baseline 基本线
 */
