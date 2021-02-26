package com.example.androidpromoteroad.drawview.textmeasure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.BitmapUtils
import com.example.androidpromoteroad.utils.dp2px
import com.wtk.nbutil.util.BitmapUtil

/**
 * author: WentaoKing
 * created on: 2020/9/5
 * description: 多行绘制
 */

class MultilineTextView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    val IMAGE_PADDING = 50.dp2px
    val IMAGE_SIZE = 150.dp2px

    private val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin dignissim lacinia massa. " +
                "Nam ac commodo lectus, a pretium tellus. Aenean lobortis semper nisl id scelerisque. " +
                "Quisque non diam id nisl ullamcorper consectetur id et diam. Morbi a arcu sapien." +
                " Vivamus laoreet metus sed sodales maximus. Donec accumsan pellentesque magna, " +
                "quis dictum sem sagittis placerat. Praesent bibendum condimentum laoreet. " +
                "Mauris pharetra libero rhoncus dignissim pretium. Maecenas laoreet massa non sapien dignissim," +
                " in tincidunt nisl luctus. Pellentesque bibendum pulvinar nisi sit amet consequat." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin dignissim lacinia massa. " +
                "Nam ac commodo lectus, a pretium tellus. Aenean lobortis semper nisl id scelerisque. " +
                "Quisque non diam id nisl ullamcorper consectetur id et diam. Morbi a arcu sapien." +
                " Vivamus laoreet metus sed sodales maximus. Donec accumsan pellentesque magna, " +
                "quis dictum sem sagittis placerat. Praesent bibendum condimentum laoreet. " +
                "Mauris pharetra libero rhoncus dignissim pretium. Maecenas laoreet massa non sapien dignissim," +
                " in tincidunt nisl luctus. Pellentesque bibendum pulvinar nisi sit amet consequat."

    private val bitmap = BitmapUtil.getImageBitmap(resources, R.drawable.avatar, IMAGE_SIZE.toInt())

    val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 25f.dp2px
        style = Paint.Style.STROKE
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制多行文字, 灵活的方法
        textPaint.getFontMetrics(fontMetrics)
        canvas.drawBitmap(bitmap, width.toFloat() - IMAGE_SIZE, IMAGE_PADDING, paint)
        val measuredWidth = floatArrayOf(0f)
        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top
        var maxWidth: Float
        while (start < text.length) {
            //如果文字的底部超过图片的顶部，或者文字的顶部超过图片的底部，就说明在图片的范围外，就全宽绘制字体，
            // 否则在图片范围内去掉，去掉图片宽度
            if (verticalOffset + fontMetrics.bottom < IMAGE_PADDING
                || verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE
            ) {
                maxWidth = width.toFloat()
            } else {
                maxWidth = width - bitmap.width.toFloat()
            }
            //截断文本
            count = textPaint.breakText(text, start, text.length, true, maxWidth, measuredWidth)
            canvas.drawText(text, start, start + count, 0f, verticalOffset, textPaint)
            start += count
            //添加竖直间距实现换行。使用paint给的字体间距更好
            verticalOffset += textPaint.fontSpacing
        }

    }

}