package com.example.androidpromoteroad.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * author: created by wentaoKing
 * date: created in 2020/8/5
 * description: 扩展属性
 */

/**
 * dp to px
 */
val Float.dp2px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )