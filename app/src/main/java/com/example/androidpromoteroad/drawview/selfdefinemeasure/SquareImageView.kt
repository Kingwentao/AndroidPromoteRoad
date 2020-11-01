package com.example.androidpromoteroad.drawview.selfdefinemeasure

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * author: created by wentaoKing
 * date: created in 2020/11/1
 * description: 自定义一个正方形view
 */
class SquareImageView(context: Context, attributeSet: AttributeSet) :
    AppCompatImageView(context, attributeSet) {

    //layout虽然可以将其变成正方形，但layout因为属于绘制比较靠后的流程，所以父布局没法对其进行干预、重新调整，
    // 很有可能导致其他不可预期的问题
/*    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        val height = b - t
        val size = min(width, height)
        super.layout(l, t, r + size, b + size)
    }*/

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth,measuredHeight)
        setMeasuredDimension(size,size)
        //虽然getWidth和getHeight是布局完的实际结果，但在测量过程一般还没有值，有也是上次的结果，不是最新鲜的，
        //所以在绘制过程，都用measuredWidth,measuredHeight的值
    }
}