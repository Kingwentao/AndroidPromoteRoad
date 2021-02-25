package com.wtk.nbutil.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.roundToInt

/**
 * @author caizeming
 * @email  caizeming@cvte.com
 * @date   2018/10/9
 * @description: 显示相关的工具类
 */
object DisplayUtil {

    /**
     * px2dp
     * @param context Context
     * @param value Float
     * @return Int
     */
    fun px2dp(context: Context, value: Float): Float {
        val density = context.resources.displayMetrics.density
        return value / density
    }

    /**
     * px2dp
     * @param resources Resources
     * @param value Float
     * @return Int
     */
    fun px2dp(resources: Resources, value: Float): Int {
        val density = resources.displayMetrics.density
        return (value / density).roundToInt()
    }

    /**
     * dp2px
     * @param context Context
     * @param value Float
     * @return Int
     */
    fun dp2px(context: Context, value: Float): Int {
        val density = context.resources.displayMetrics.density
        return (value * density).roundToInt()
    }

    /**
     * dp2px
     * @param context Context
     * @param value Float
     * @return Int
     */
    fun dp2px(resources: Resources, value: Float): Int {
        val density = resources.displayMetrics.density
        return (value * density).roundToInt()
    }

    /**
     * dp2px
     * @param context Context
     * @param value Float
     * @return Float
     */
    fun dp2pxFloat(context: Context, value: Float): Float {
        val density = context.resources.displayMetrics.density
        return value * density
    }

    /**
     * 获得屏幕宽度
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获得屏幕高度
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

}
