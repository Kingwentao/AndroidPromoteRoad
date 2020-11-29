package com.example.androidpromoteroad.drawview.scalableimageview

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.OverScroller
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GestureDetectorCompat
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.BitmapUtils
import com.example.androidpromoteroad.utils.dp2px
import kotlin.math.max
import kotlin.math.min

/**
 * author: created by wentaoKing
 * date: created in 11/22/20
 * description:
 */
const val TAG = "ScalableImageView"

class ScalableImageView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    //是否放大
    private var isZoom: Boolean = false
    private val mFilingRunner = FilingRunner()
    private val mGestureDetector = MyGestureDetector()
    private var scaleFraction: Float = 0f
        set(value) {
            if (value != field) {
                field = value
                invalidate()
            }
        }

    //定义一个缩放动画效果
    private val scaleAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    //初始偏移量
    private var originOffsetX = 0f
    private var originOffsetY = 0f

    private var EXTRACT_FACTOR = 1.2f

    //滑动偏移量
    private var offsetX = 0f
    private var offsetY = 0f
    private var mSmallScale = 0f
    private var mBigScale = 0f
    private var mAvatar = BitmapUtils.getBitmap(resources, R.drawable.avatar, 500)
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //如果是同个对象，可以不用设置setOnDoubleTapListener，初始化的时候内部已经设置双击监听
    private var gestureDetector: GestureDetectorCompat =
        GestureDetectorCompat(context, mGestureDetector)
//        .apply {
//            setOnDoubleTapListener(this@ScalableImageView)
//        }

    //OverScroller比Scroller效果好，它滑动时可以设置一个over的区域，并且初始滑动速度跟随手指速度
    private var scroller = OverScroller(this.context)
    // private var scroller = Scroller(this.context)


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //return super.onTouchEvent(event)
        //使用gestureDetector代替super.onTouchEvent(event)，实现双击操作
        return gestureDetector.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(TAG, "onSizeChanged: w: $w h: $h")
        //计算把图片居中需要的偏移量
        originOffsetX = ((width - mAvatar.width) / 2).toFloat()
        originOffsetY = ((height - mAvatar.height) / 2).toFloat()
        //根据图片宽高比与屏幕的宽高比进行比较，获取最佳缩放比(简而言之：胖的图片需要横向充满，瘦的图片需要竖向充满)
        if (width / height < mAvatar.width / mAvatar.height) {
            mSmallScale = width / mAvatar.width.toFloat()
            mBigScale = height / mAvatar.height.toFloat() * EXTRACT_FACTOR
        } else {
            mSmallScale = height / mAvatar.height.toFloat()
            mBigScale = width / mAvatar.width.toFloat() * EXTRACT_FACTOR
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scale = mSmallScale + scaleFraction * (mBigScale - mSmallScale)
        //倒着看代码：先绘制 -》 然后缩放 -》 移动  ps: 这里乘以 scaleFraction 是为了跟随动画节奏，避免改值太快出现闪动
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        Log.d(TAG, "onDraw: sacle: $scale")
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(mAvatar, originOffsetX, originOffsetY, mPaint)
    }

    /**
     * 修正偏移量，解决放大后未填充满屏幕的问题（比如在边缘处点击放大）
     */
    private fun fixOffset() {
        val maxOffsetX = (mAvatar.width * mBigScale - width) / 2
        val maxOffsetY = (mAvatar.height * mBigScale - height) / 2
        offsetX = min(offsetX, maxOffsetX)
        offsetX = max(offsetX, -maxOffsetX)
        offsetY = min(offsetY, maxOffsetY)
        offsetY = max(offsetY, -maxOffsetY)
    }


    inner class MyGestureDetector : android.view.GestureDetector.SimpleOnGestureListener() {
        //只收到双击后的Down事件
        override fun onDoubleTap(e: MotionEvent): Boolean {
            isZoom = !isZoom
            if (isZoom) {
                //实现以手指触摸点的位置去放大：计算以中心点放大后的触摸点偏移量，再初始时就反向偏移回去
                offsetX = (e.x - width / 2) * (1 - mBigScale / mSmallScale)
                offsetY = (e.y - height / 2) * (1 - mBigScale / mSmallScale)
                fixOffset()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }

        override fun onScroll(
            downEvent: MotionEvent?,
            curEvent: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.d(TAG, "onScroll: x = $distanceX")
            if (!isZoom) {
                return false
            }
            fixOffset()
            //因为给的参数distanceX值比较奇怪，偏移量是负值，所以需要-=
            offsetX -= distanceX
            offsetY -= distanceY
            invalidate()
            return true
        }

        //单击监听的替代方法
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            //返回值是给系统记录是否消费的，对我们开发者没有用，因为是否消费在down事件就决定了，和这里没有关系
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            //控制gestureDetector.onTouchEvent是否接收事件序列
            return true
        }

        //快速滑动，然后松手的行为
        override fun onFling(
            downEvent: MotionEvent?,
            curEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (!isZoom) {
                return false
            }
            val startX = offsetX.toInt()
            val startY = offsetY.toInt()
            val minX = (-(mAvatar.width * mBigScale - width) / 2).toInt()
            val maxX = ((mAvatar.width * mBigScale - width) / 2).toInt()
            val minY = (-(mAvatar.height * mBigScale - height) / 2).toInt()
            val maxY = ((mAvatar.height * mBigScale - height) / 2).toInt()
            //设置overX，overY 可以让滑动边界扩大
            val overX = 40f.dp2px.toInt()
            val overY = 40f.dp2px.toInt()
            scroller.fling(
                startX, startY, velocityX.toInt(), velocityY.toInt(), minX, maxX, minY,
                maxY, overX, overY
            )
            //invalidate() 或者 post太重了，会在一帧动画里面多次invalidate()，postOnAnimation 会在下一帧执行
            postOnAnimation(mFilingRunner)
            //post(this)
            return true
        }
    }

    //执行filing后的动画任务
    inner class FilingRunner : Runnable {
        override fun run() {
            //If it returns true, the  animation is not yet finished.
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)
            }
        }

    }
}