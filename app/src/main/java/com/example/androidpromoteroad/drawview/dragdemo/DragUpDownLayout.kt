package com.example.androidpromoteroad.drawview.dragdemo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

/**
 * author: created by wentaoKing
 * date: created in 1/2/21
 * description: 使用ViewDragHelper实现拖拽功能：ViewDragHelper的使用主要针对视图拖拽，不包含数据
 */
class DragUpDownLayout(context: Context,attrs: AttributeSet?): FrameLayout(context,attrs) {

    private var mDragListener = DragCallback()
    private var mDragHelper: ViewDragHelper = ViewDragHelper.create(this, mDragListener)
    private var mViewConfiguration: ViewConfiguration = ViewConfiguration.get(context)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        Log.d("wtk","computeScroll")
        if (mDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    inner class DragCallback: ViewDragHelper.Callback(){

        //是否抓住view，这个方法必须实现，其他都可不实现
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            //默认值是0，表示不拖动，返回top表示允许向上拖
            return top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            Log.d("wtk","onViewReleased")
            if (Math.abs(yvel) > mViewConfiguration.scaledMinimumFlingVelocity){
                if (yvel > 0) {
                    mDragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                } else {
                    mDragHelper.settleCapturedViewAt(0, 0)
                }
            }else{
                if (releasedChild.top < height - releasedChild.bottom) {
                    mDragHelper.settleCapturedViewAt(0, 0)
                }else{
                    mDragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                }
            }
            postInvalidateOnAnimation()
        }

        //被拖拽时会被回调
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
        }

    }
}