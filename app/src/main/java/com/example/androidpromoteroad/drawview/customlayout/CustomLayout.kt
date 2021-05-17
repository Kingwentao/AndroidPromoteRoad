package com.example.androidpromoteroad.drawview.customlayout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import java.lang.IllegalArgumentException

/**
 * author: created by wentaoKing
 * date: created in 5/16/21
 * description: 自定义layout通用方法
 */
abstract class CustomLayout(context: Context) : ViewGroup(context) {

    protected fun View.defaultWidthMeasureSpec(parent: ViewGroup): Int {
        return when (layoutParams.width) {
            LayoutParams.MATCH_PARENT -> parent.measuredWidth.toExactlyMeasureSpec()
            LayoutParams.WRAP_CONTENT -> LayoutParams.WRAP_CONTENT.toAtMostMeasureSpec()
            0 -> throw IllegalArgumentException("layout param is not allow zero!")
            else -> layoutParams.width.toExactlyMeasureSpec()
        }
    }

    protected fun View.defaultHeightMeasureSpec(parent: ViewGroup): Int {
        return when (layoutParams.height) {
            LayoutParams.MATCH_PARENT -> parent.measuredHeight.toExactlyMeasureSpec()
            LayoutParams.WRAP_CONTENT -> LayoutParams.WRAP_CONTENT.toAtMostMeasureSpec()
            0 -> throw IllegalArgumentException("layout param is not allow zero!")
            else -> layoutParams.height.toExactlyMeasureSpec()
        }
    }

    protected fun Int.toExactlyMeasureSpec(): Int {
        return MeasureSpec.makeMeasureSpec(this, MeasureSpec.EXACTLY)
    }

    protected fun Int.toAtMostMeasureSpec(): Int {
        return MeasureSpec.makeMeasureSpec(this, MeasureSpec.AT_MOST)
    }

    protected fun View.autoMeasure() {
        measure(
            defaultWidthMeasureSpec(this@CustomLayout),
            defaultHeightMeasureSpec(this@CustomLayout)
        )
    }

    protected fun View.layout(x: Int, y: Int, isFromRight: Boolean = false) {
        if (!isFromRight) {
            layout(x, y, x + measuredWidth, y + measuredHeight)
        } else {
            layout(this@CustomLayout.measuredWidth - measuredWidth - x, y)
        }
    }

    protected fun View.measuredWidthWithMargin() = measuredWidth + marginLeft + marginRight
    protected fun View.measuredHeightWithMargin() = measuredWidth + marginLeft + marginRight
}