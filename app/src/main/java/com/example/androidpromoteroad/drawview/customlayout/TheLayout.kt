package com.example.androidpromoteroad.drawview.customlayout

import android.content.Context
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginLeft
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

    private val headerBanner = AppCompatImageView(context).apply {
        scaleType = ImageView.ScaleType.CENTER
        setImageResource(R.drawable.aerbeisishan)
        layoutParams = LayoutParams(MATCH_PARENT, 150.dp2px.toInt())
        addView(this)
    }

    private val avatar = AppCompatImageView(context).apply {
        setImageResource(R.drawable.avatar)
        layoutParams = LayoutParams(60.dp2px.toInt(), 60.dp2px.toInt())
        addView(this)
    }

    private val avatarName = AppCompatTextView(context).apply {
        text = "WTKING"
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        textSize = 8.dp2px
        addView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测量每个view的大小
        headerBanner.autoMeasure()
        avatar.autoMeasure()
        val nameWidth = measuredWidth - avatar.measuredWidth
        avatarName.measure(
            nameWidth.toExactlyMeasureSpec(),
            avatarName.defaultHeightMeasureSpec(this)
        )
        //计算banner下的高度
        val max = avatar.marginTop + avatarName.measuredHeightWithMargin()
            .coerceAtLeast(avatar.measuredHeight)
        //计算整个wrap_content的高度
        val wrapContentHeight = headerBanner.measuredHeightWithMargin() + max
        Log.d(TAG, "onMeasure: max: $max ch: $wrapContentHeight")
        setMeasuredDimension(measuredWidth, wrapContentHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        headerBanner.layout(0, 0)
        Log.d(TAG, "onLayout: height: ${headerBanner.measuredHeight}")
        avatar.let {
            it.layout(it.marginLeft, headerBanner.bottom + marginTop)
        }
        avatarName.let {
            it.layout(0, avatar.top + it.marginTop, true)
        }
    }

}