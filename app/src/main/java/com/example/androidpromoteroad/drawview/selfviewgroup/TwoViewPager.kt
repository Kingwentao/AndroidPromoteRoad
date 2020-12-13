package com.example.androidpromoteroad.drawview.selfviewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

/**
 * author: created by wentaoKing
 * date: created in 12/13/20
 * description: 定义可左右滑动的view页面
 */
class TwoViewPager(context: Context, attributeSet: AttributeSet) :
    ViewGroup(context, attributeSet) {

    private var downX = 0f
    private var downY = 0f
    private var downScrollX = 0f
    private var scrolling = false
    private val overScroller = OverScroller(context)
    private val viewConfiguration = ViewConfiguration.get(context)
    private val velocityTracker = VelocityTracker.obtain()
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //给child view固定宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height
        for (child in children) {
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var result = false
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = downX - ev.x
                if (!scrolling) {
                    if (abs(dx) > pagingSlop) {
                        scrolling = true
                        parent.requestDisallowInterceptTouchEvent(true)
                        result = true
                    }
                }
            }
        }
        return result
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (downX - ev.x + downScrollX).toInt()
                    .coerceAtLeast(0)
                    .coerceAtMost(width)
                //记得该方法参数值是倒着写的
                scrollTo(dx, 0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                //如果速度慢，就判断滑动有没有超过屏幕一半
                val targetPage = if (abs(vx) < minVelocity) {
                    if (scrollX > width / 2) 1 else 0
                } else {
                    //如果速度够快，直接判断方向
                    if (vx < 0) 1 else 0
                }
                val scrollDistance = if (targetPage == 1) width - scrollX else -scrollX
                overScroller.startScroll(getScrollX(), 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    /**
     * 每次invalidate()，都会调用此方法
     */
    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            //该方法会标记失效，--> invalidate()-->computeScroll-->postInvalidateOnAnimation() 【形成循环】
            postInvalidateOnAnimation()
        }
    }
}