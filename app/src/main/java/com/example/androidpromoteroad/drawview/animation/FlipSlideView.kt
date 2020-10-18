package com.example.androidpromoteroad.drawview.animation

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.BitmapUtils
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 2020/10/11
 * description: 翻页效果动画view
 */
class FlipSlideView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    val IMAGE_SIZE = 300F.dp2px
    val IMAGE_PADDING = 50F.dp2px

    private val bitmap = BitmapUtils.getBitmap(resources, R.drawable.avatar, IMAGE_SIZE.toInt())
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val mCamera = Camera()

    //下半部分翻转角度
    private var bottomFlip = 0f
        set(value) {
            field = value
            invalidate()
        }

    //上半部分翻转角度
    private var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }

    //旋转角度
    private var flipRotate = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        //默认camera的z轴位置是-8（非像素），px == 8*72 像素
        //为了保证所有手机效果一样，z轴距离应该屏幕适配： 比如 -8 * 屏幕密度值
        mCamera.setLocation(0f, 0f, -8f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //4. 翻页效果： 三维旋转通过camera + 倒着写思维方式
        canvas?.withSave {
            //4.1 变化并绘制上半部分的直立效果：
            // 再移动到实际目标的位置
            canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
            //记得要旋转回来
            canvas.rotate(-flipRotate)
            //
            mCamera.save()
            mCamera.rotateX(topFlip)
            mCamera.applyToCanvas(canvas)
            mCamera.restore()
            //此时位于原点，直接用图片大小就可以计算出裁剪区域
            canvas.clipRect(
                -IMAGE_SIZE, -IMAGE_SIZE,
                IMAGE_SIZE,
                0f
            )
            //这里旋转一下，实现斜切的翻页效果
            canvas.rotate(flipRotate)
            //移动到原始坐标系原点
            canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
            canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, mPaint)
        }

        canvas?.withSave {
            //4.2 下半部分的翻页效果：再移动到实际目标的位置
            canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
            //记得要旋转回来
            canvas.rotate(-flipRotate)
            mCamera.save()
            mCamera.rotateX(bottomFlip)
            mCamera.applyToCanvas(canvas)
            mCamera.restore()
            //此时位于原点，直接用图片大小就可以计算出裁剪区域（这里 bottom和right用IMAGE_SIZE 没有用它IMAGE_SIZE/2是为了多切一点，解决因为旋转导致的裁剪多裁问题）
            canvas.clipRect(-IMAGE_SIZE, 0f, IMAGE_SIZE, IMAGE_SIZE)
            //这里旋转一下，实现斜切的翻页效果
            canvas.rotate(flipRotate)
            //移动到原始坐标系原点
            canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
            canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, mPaint)
        }

    }
}