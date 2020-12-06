package com.example.androidpromoteroad.drawview.multitouchview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.BitmapUtils

/**
 * author: created by wentaoKing
 * date: created in 12/6/20
 * description: 接力风格的多指触摸：找到实际操作的那个手指的触摸点，并追踪它
 */
class MultiTouchView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var offsetX = 0f
    private var offsetY = 0f
    private var downY = 0f
    private var downX = 0f
    private var originTransX = 0f
    private var originTransY = 0f
    private var mAvatar = BitmapUtils.getBitmap(resources, R.drawable.avatar, 500)
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mTrackingPointerId = 0


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mAvatar, offsetX, offsetY, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                //action_down事件不存在多指操作，所以pointerIndex肯定是0
                mTrackingPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                originTransX = offsetX
                originTransY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                //获取触发此时action的index
                val actionIndex = event.actionIndex
                mTrackingPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                originTransX = offsetX
                originTransY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                //获取此时追踪的手指的index，为了获取它的坐标（ps：不直接用getX，是因为手指的index是不断变化的，
                //而它的id是不变的，所以需要保存下需要的id，然后在find它的index，从而得到准确的坐标）
                val index = event.findPointerIndex(mTrackingPointerId)
                offsetX = event.getX(index) - downX + originTransX
                offsetY = event.getY(index) - downY + originTransY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                // 如果抬起的手指是当前追踪的手指，那么由最后一个index来接力
                if (pointerId == mTrackingPointerId) {
                    val newIndex = if (actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    mTrackingPointerId = event.getPointerId(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                    originTransX = offsetX
                    originTransY = offsetY
                }
            }
        }
        return true
    }
}