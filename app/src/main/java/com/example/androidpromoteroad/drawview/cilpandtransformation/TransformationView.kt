package com.example.androidpromoteroad.drawview.cilpandtransformation

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.BitmapUtils
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 2020/10/11
 * description: 几何变换、范围裁剪的示例代码
 */
class TransformationView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    val IMAGE_SIZE = 200F.dp2px
    val IMAGE_PADDING = 50F.dp2px

    private val bitmap = BitmapUtils.getBitmap(resources, R.drawable.avatar, IMAGE_SIZE.toInt())
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    private val mCamera = Camera()

    init {
        mCamera.rotateX(65f)
        //默认camera的z轴位置是-8（非像素），px == 8*72 像素
        //为了保证所有手机效果一样，z轴距离应该屏幕适配： 比如 -8 * 屏幕密度值
        mCamera.setLocation(0f, 0f, -8f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.save()
//        //1. 正向思考，以坐标系为核心思考（要时刻记得坐标系在哪里，很难）：平移坐标系后，旋转坐标系
//        canvas?.translate(100f, 0f)
//        canvas?.rotate(45F, IMAGE_SIZE / 2, IMAGE_SIZE / 2)
//        canvas?.drawBitmap(bitmap, 0f, 0f, mPaint)
//        canvas?.restore()

//        //2.反向写代码：以变化的物体为核心,就不需要考虑canvas坐标系就能画对
//        canvas?.rotate(45F,  100f+IMAGE_SIZE / 2, 100f+IMAGE_SIZE / 2)
//        canvas?.translate(100f, 0f)
//        canvas?.drawBitmap(bitmap, 0f, 0f, mPaint)


//        canvas?.save()
//        canvas?.drawLine(2f, 0f, 2f, 1000f, mPaint2)
//        canvas?.drawLine(200f, 0f, 200f, 1000f, mPaint2)
//        canvas?.restore()
//        //3. 倒着思考 写示例：
//        canvas?.scale(2f, 2f)
//        canvas?.drawBitmap(bitmap, 100f, 0f, mPaint)


        //4. 翻页效果： 三维旋转通过camera + 倒着写思维方式

        canvas?.save()
        //4.1 变化并绘制上半部分的直立效果：
        // 再移动到实际目标的位置
        canvas?.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        //记得要旋转回来
        canvas?.rotate(-45f)
        //此时位于原点，直接用图片大小就可以计算出裁剪区域
        canvas?.clipRect(
            -IMAGE_SIZE, -IMAGE_SIZE,
            IMAGE_SIZE,
            0f
        )
        //这里旋转一下，实现斜切的翻页效果
        canvas?.rotate(45f)
        //移动到原始坐标系原点
        canvas?.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
        canvas?.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, mPaint)
        canvas?.restore()

        canvas?.save()
        //4.2 下半部分的翻页效果：再移动到实际目标的位置
        canvas?.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        //记得要旋转回来
        canvas?.rotate(-45f)
        mCamera.applyToCanvas(canvas)
        //此时位于原点，直接用图片大小就可以计算出裁剪区域（这里 bottom和right用IMAGE_SIZE 没有用它IMAGE_SIZE/2是为了多切一点，解决因为旋转导致的裁剪多裁问题）
        canvas?.clipRect(-IMAGE_SIZE, 0f, IMAGE_SIZE, IMAGE_SIZE)
        //这里旋转一下，实现斜切的翻页效果
        canvas?.rotate(45f)
        //移动到原始坐标系原点
        canvas?.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
        canvas?.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, mPaint)
        canvas?.restore()
    }
}