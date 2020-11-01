package com.example.androidpromoteroad.drawview.selfdefinelayout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

/**
 * author: created by wentaoKing
 * date: created in 2020/11/1
 * description:自定义TagLayout
 */
class TagLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {

    //存储计算完成后最终布局的tag位置
    private val tagBoundsList = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        var curLinWidthUsed = 0
        var heightUsed = 0
        var lineMaxHeight = 0
        var maxWidth = 0
        for ((index, child) in children.withIndex()) {
            //获取子view真实的测量数据（要根据子view在布局里面的LayoutParam、父布局的SpecMode和给予的可用空间）
            //使用此方法，需要充血generateLayoutParams方法，否则会报错
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                curLinWidthUsed,
                heightMeasureSpec,
                heightUsed
            )

            //行的最大高度
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
            //如果不存在就新添加一个
            if (index >= tagBoundsList.size) {
                tagBoundsList.add(Rect())
            }
            //如果不是非精确模式并且即将超过最大宽度，直接换行，重置一些值
            if (specWidthMode != MeasureSpec.UNSPECIFIED && curLinWidthUsed + child.measuredWidth > specWidthSize) {
                maxWidth = curLinWidthUsed
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                curLinWidthUsed = 0
            }

            //存储子view的位置
            val childBound = tagBoundsList[index]
            childBound.set(
                curLinWidthUsed,
                heightUsed,
                curLinWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )

            curLinWidthUsed += child.measuredWidth
            maxWidth = max(curLinWidthUsed, maxWidth)
        }
        val lastWidth = maxWidth
        val lastHeight = heightUsed + lineMaxHeight
        setMeasuredDimension(lastWidth, lastHeight)
    }


    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val childBound = tagBoundsList.get(index)
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom)
        }
    }

}
