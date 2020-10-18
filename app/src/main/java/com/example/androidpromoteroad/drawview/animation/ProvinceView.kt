package com.example.androidpromoteroad.drawview.animation

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.androidpromoteroad.utils.dp2px
import kotlin.math.cos
import kotlin.math.sin

/**
 * author: created by wentaoKing
 * date: created in 2020/20/18
 * description:  省份切换的动画View
 */
val provinces = listOf(
    "北京市",
    "天津市",
    "上海市",
    "重庆市",
    "河北省",
    "山西省",
    "辽宁省",
    "吉林省",
    "黑龙江省",
    "江苏省",
    "浙江省",
    "安徽省",
    "福建省",
    "江西省",
    "山东省",
    "河南省",
    "湖北省",
    "湖南省",
    "广东省",
    "海南省",
    "四川省",
    "贵州省",
    "云南省",
    "陕西省",
    "甘肃省",
    "青海省",
    "台湾省",
    "内蒙古自治区",
    "广西壮族自治区",
    "西藏自治区",
    "宁夏回族自治区",
    "新疆维吾尔自治区",
    "香港特别行政区",
    "澳门特别行政区"
)

class ProvinceView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    init {
        //这里有个误区：setLayerType很多人以为是开启硬件加速或者软件加速，其实android不支持view层级的硬件加速设置
        //setLayerType只是说使用 硬件的离屏缓冲，所以可以享用硬件下带来的性能优势，也可以变相的理解开启硬件加速吧。但本质上并不是开启硬件加速
        setLayerType(LAYER_TYPE_HARDWARE, null)
        //软件下的离屏缓冲
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        //不使用离屏缓冲
        setLayerType(LAYER_TYPE_NONE, null)
    }

    var province: String = provinces[0]
        set(value) {
            field = value
            invalidate()
        }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 40f.dp2px
        strokeWidth = 15f.dp2px
        color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(province, width / 2f, height / 2f, paint)
    }

}

/**
 * 自定义一个字符串的估值器
 */
class ProvinceEvaluator : TypeEvaluator<String> {
    override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
        val startIndex = provinces.indexOf(startValue)
        val endIndex = provinces.indexOf(endValue)
        val currentIndex = startIndex + ((endIndex - startIndex) * fraction).toInt()
        return provinces[currentIndex]
    }
}