package com.example.androidpromoteroad.drawview.drawableandbitmap

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * author: created by wentaoKing
 * date: created in 2020/10/25
 * description: drawable view
 */
class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val drawable = MeshDrawable()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制之前需要设置边界,和bitmap不一样，drawable像是一个绘制工具
        drawable.setBounds(0,0,right,bottom)
        drawable.draw(canvas)
    }
}