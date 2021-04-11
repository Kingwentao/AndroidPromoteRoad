package com.example.androidpromoteroad.drawview.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: WentaoKing
 * created on: 2020/8/22
 * description: xfermode 的使用
 */

private val IMAGE_WIDTH = 250f.dp2px
private val IMAGE_PADDING = 20f.dp2px
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

/**
 * 学习xfermodeView的使用
 * 包括官方示例和圆形头像实战
 */
class XfermodeView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectBounds = RectF(
        IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH,
        IMAGE_PADDING + IMAGE_WIDTH
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //圆形头像
        //drawCircleAvatarView(canvas)
        //官方范例
        drawOfficialExample(canvas)
    }

    private fun getImageBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
    }

    /**
     * 绘制一个圆形的头像
     */
    private fun drawCircleAvatarView(canvas: Canvas) {
        //准备一个离屏缓冲（区域要尽可能的小，满足条件大小就好，它比较耗费性能），在透明缓冲区域绘制
        //若不使用离屏缓冲，直接绘制就没有效果：画板的整个背景会参与模式的使用，导致效果不对
        val count = canvas.saveLayer(rectBounds, null)
        canvas.drawOval(rectBounds, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getImageBitmap(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        paint.xfermode = null
        //释放离屏缓冲
        canvas.restoreToCount(count)
    }

    private val circleBitmap =
        Bitmap.createBitmap(200f.dp2px.toInt(), 200f.dp2px.toInt(), Bitmap.Config.ARGB_8888)
    private val squareBitmap =
        Bitmap.createBitmap(200f.dp2px.toInt(), 200f.dp2px.toInt(), Bitmap.Config.ARGB_8888)

    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#D81B60")
        canvas.drawOval(0f, 100f.dp2px, 100f.dp2px, 200f.dp2px, paint)
        canvas.setBitmap(squareBitmap)
        paint.color = Color.parseColor("#2196F3")
        canvas.drawRect(50f.dp2px, 50f.dp2px, 150f.dp2px, 150f.dp2px, paint)
    }

    /**
     * 绘制官方案例：圆与矩形的xfremode
     * 不能直接绘制图形，xfermode的使用不是两个图形的叠加，而是对两块区域的操作。只通过图形会导致参与的只是图形区域，
     * 这样区域不够，最终的效果也是不对的。
     * 所以需要要通过bitmap设定区域大小，并存储绘制的内容
     */
    private fun drawOfficialExample(canvas: Canvas) {
        val bounds = RectF(0f, 0f, 150f.dp2px, 200f.dp2px)
        val count = canvas.saveLayer(bounds, null)
        canvas.drawBitmap(circleBitmap, 0f, 0f, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(squareBitmap, 0f, 0f, paint)
        paint.xfermode = null
        //释放离屏缓冲
        canvas.restoreToCount(count)
    }

}