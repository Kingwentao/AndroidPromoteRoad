package com.wtk.nbutil

import android.graphics.PointF
import android.view.View

/**
 * author: WentaoKing
 * created on: 1/25/21
 * description: View相关的扩展方法
 */

/**
 * 获取view在窗口的位置
 */
fun View.getLocationInWindow(): PointF {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    val x = location[0].toFloat()
    val y = location[1].toFloat()
    return PointF(x, y)
}