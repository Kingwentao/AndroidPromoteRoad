package com.example.androidpromoteroad.drawview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 2020/8/5
 * description: 用来学习图形的位置和尺寸测量
 */
class FirstView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path()
    val radius = 100f.dp2px

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        path.addCircle(width / 2f, height / 2f, radius, Path.Direction.CCW)
        path.addRect(
            width / 2f - radius, height / 2f - radius, width / 2f + radius,
            height / 2f + radius, Path.Direction.CCW
        )
        //EVEN_ODD 相交区域直接镂空，不区分绘制图形的方向
        //WINDING 会根据绘制方向判断填充和镂空，是根据一个算法来判断的
        path.fillType = Path.FillType.EVEN_ODD

        //第二个参数表示Path是否闭合
        val pathMeasure = PathMeasure(path, true)
        val result = pathMeasure.length
        println(result)
    }
}