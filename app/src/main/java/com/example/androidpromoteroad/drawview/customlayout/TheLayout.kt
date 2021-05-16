package com.example.androidpromoteroad.drawview.customlayout

import android.content.Context
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 5/16/21
 * description: 通过自定义viewGroup代替xml文件
 */
class TheLayout(context: Context) : CustomLayout(context) {

    companion object {
        private const val TAG = "TheLayout"
    }

    val header = AppCompatImageView(context).apply {
        scaleType = ImageView.ScaleType.FIT_XY
        setImageResource(R.drawable.avatar)
        layoutParams = LayoutParams(150.dp2px.toInt(), 50.dp2px.toInt())
        addView(this)
    }

    val avatar = AppCompatImageView(context).apply {
        setImageResource(R.drawable.avatar)
        layoutParams = LayoutParams(30.dp2px.toInt(), 30.dp2px.toInt())
        addView(this)
    }

    val messageTitle = AppCompatTextView(context).apply {
        text = "AppCompatTextView"
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        textSize = 15.dp2px
        addView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测量每个view的大小
        header.autoMeasure()
        avatar.autoMeasure()
        val messageWidth = measuredWidth - messageTitle.marginLeft -
                messageTitle.marginRight - avatar.measuredWidthWithMargin()
        messageTitle.measure(
            messageWidth.toExactlyMeasureSpec(),
            messageTitle.defaultHeightMeasureSpec(this)
        )

        val max = avatar.marginTop + messageTitle.measuredHeightWithMargin()
            .coerceAtLeast(avatar.measuredHeight)

        //计算整个wrap_content的高度
        val wrapContentHeight = header.measuredHeightWithMargin() + max
        Log.d(TAG, "onMeasure: max: $max ch: $wrapContentHeight")
        setMeasuredDimension(measuredWidth, wrapContentHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        header.layout(0, 0)
        Log.d(TAG, "onLayout: height: ${header.measuredHeight}")
        avatar.let {
            it.layout(it.marginLeft, header.bottom + marginTop)
        }
        messageTitle.let {
            it.layout(avatar.right + it.marginLeft, avatar.top + it.marginTop)
        }
    }

}