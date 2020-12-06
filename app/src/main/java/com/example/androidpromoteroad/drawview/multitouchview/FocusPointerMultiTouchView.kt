package com.example.androidpromoteroad.drawview.multitouchview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.BitmapUtils

/**
 * author: created by wentaoKing
 * date: created in 12/6/20
 * description: 配合风格的多指触摸：找到所有手指的中心位置，作为虚拟手指进行计算
 */
class FocusPointerMultiTouchView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

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
        var focusX = 0f
        var focusY = 0f
        var sumX = 0f
        var sumY = 0f
        var pointerCount = event.pointerCount
        var isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        Log.d("FocusPointerMultiTouch","pointCount: $pointerCount")
        for (i in 0 until pointerCount){
            if (!(isPointerUp && event.actionIndex == i)){
                sumX += event.getX(i)
                sumY = event.getY(i)
            }else{
                Log.d("FocusPointerMultiTouch","多指（i = $i）是抬起操作，" +
                        "此时count: $pointerCount 并不是抬起后的个数（和视觉上不一致），所以这颗手指不纳入计算")
            }
        }
        if (isPointerUp) {
            pointerCount -= 1
            Log.d("FocusPointerMultiTouch"," 抬起操作，" +
                    "此时count: $pointerCount 并不是抬起后的个数（和视觉上不一致），所以纳入计算的总数要减1")
        }
        focusX = sumX / pointerCount
        focusY = sumY / pointerCount
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN , MotionEvent.ACTION_POINTER_DOWN,MotionEvent.ACTION_POINTER_UP -> {
                //action_down事件不存在多指操作，所以pointerIndex肯定是0
                mTrackingPointerId = event.getPointerId(0)
                downX = focusX
                downY = focusY
                originTransX = offsetX
                originTransY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                offsetX = focusX - downX + originTransX
                offsetY = focusY - downY + originTransY
                invalidate()
            }
        }
        return true
    }
}