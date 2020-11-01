package com.example.androidpromoteroad.drawview.selfdefinemeasure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 2020/11/1
 * description: 完全自定义一个圆形view
 */
val PADDING = 50f.dp2px
val RADIUS = 100f.dp2px

class CircleView(context: Context, attr: AttributeSet) : View(context, attr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((PADDING + RADIUS) * 2).toInt()
        //结合父view的要求（mode和size），由于这个方法是通用的，所以官方抽了个方法resolveSize()
//        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
//        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val width = when(specWidthMode){
//            MeasureSpec.EXACTLY -> specWidthSize
//            MeasureSpec.AT_MOST -> max(size,specWidthSize)
//            else -> size
//        }
        //直接拿到修正后的结果
        val width = resolveSize(size, widthMeasureSpec)
        val height = resolveSize(size, heightMeasureSpec)
        //保存结果，给后面流程使用
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint)
    }
}