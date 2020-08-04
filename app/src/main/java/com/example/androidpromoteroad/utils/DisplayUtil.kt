package com.example.androidpromoteroad.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * author: created by wentaoKing
 * date: created in 2020/8/5
 * description: 屏幕显示工具： 扩展方法
 */

/**
 * dp to px
 */
fun Float.dp2px(value: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        Resources.getSystem().displayMetrics
    )
}
