package com.example.androidpromoteroad.constraintlayout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.hypot

/**
 * author: created by wentaoKing
 * date: created in 1/3/21
 * description: 自定义动画助手，对约束布局的多个控件统一做动画
 */
class CircularRevealHelper(context: Context, attrs: AttributeSet) :
    ConstraintHelper(context, attrs) {

    //xml布局完成后会回掉该方法
    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)
        //对多个引用控件做动画
        referencedIds.forEach {
            val view = container.getViewById(it)
            val radius = hypot(view.width.toDouble(), view.height.toDouble())
            ViewAnimationUtils.createCircularReveal(
                view, 0, 0,
                0f, radius.toFloat()
            ).setDuration(1000).start()
        }
    }
}