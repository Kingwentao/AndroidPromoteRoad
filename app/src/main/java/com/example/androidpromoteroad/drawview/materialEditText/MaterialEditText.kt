package com.example.androidpromoteroad.drawview.materialEditText

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.utils.dp2px

/**
 * author: created by wentaoKing
 * date: created in 2020/11/1
 * description: 自定义EditText
 */
private val TEXT_SIZE = 16.dp2px
private val TEXT_MARGIN = 12.dp2px
private val VERTICAL_OFFSET = 20.dp2px
private val HORIZONTAL_OFFSET = 16.dp2px

class MaterialEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    private var isShowLabel: Boolean = false
    set(value) {
        if (value != isShowLabel){
            field = value
            if (field){
                setPadding(
                    paddingLeft, (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(), paddingRight, paddingBottom)

            }

        }
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var labelShowing = false
    var labelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.textSize = TEXT_SIZE
        //这里会遍历xml文件的所有属性，把不在R.styleable.MaterialEditText中的过滤掉
        val typeArrays = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        //getXXX的第一个参数attr对应的index(ex: MaterialEditText_isShowLabel : 0)
        isShowLabel = typeArrays.getBoolean(R.styleable.MaterialEditText_isShowLabel,true)
        typeArrays.recycle()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        if (!isShowLabel) return

        //设计无到有的动画，设置比率0 -> 1
        val noToHasAnimator = ObjectAnimator.ofFloat(this, "labelFraction", 0f, 1f)
        //从无到有
        if (!text.isNullOrEmpty() && !labelShowing) {
            noToHasAnimator.setDuration(1000).start()
            labelShowing = true
        } else if (labelShowing && text.isNullOrEmpty()) {
            noToHasAnimator.setDuration(1000).reverse()
            labelShowing = false
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (labelFraction * 0xff).toInt()
        val verticalOffset = VERTICAL_OFFSET + 10f * (1 - labelFraction)
        println("ve = $verticalOffset")
        canvas.drawText(hint.toString(), HORIZONTAL_OFFSET, verticalOffset, paint)
    }
}