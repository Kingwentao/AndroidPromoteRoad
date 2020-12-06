package com.example.androidpromoteroad.drawview.multitouchview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 12/6/20
 * description: 多指各自为战风格：多指绘图view，记录每个手指的path
 */
class MultiPointerDrawView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //<pointId,Path>
    private val paths = SparseArray<Path>()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f.dp2px
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        for (i in 0 until paths.size()) {
            val path = paths.valueAt(i)
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val path = Path()
                val actionIndex = event.actionIndex
                val actionX = event.getX(actionIndex)
                val actionY = event.getY(actionIndex)
                path.moveTo(actionX, actionY)
                val pointerId = event.getPointerId(actionIndex)
                paths.append(pointerId, path)
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until paths.size()) {
                    val pointerId = event.getPointerId(i)
                    val path = paths.get(pointerId)
                    path.lineTo(event.getX(i), event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                val path = paths.get(pointerId)
                paths.remove(pointerId)
                invalidate()
            }
        }
        return true
    }
}