package com.example.androidpromoteroad.drawview.dragdemo

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.layout_drag_to_destinaton.view.*

/**
 * author: created by wentaoKing
 * date: created in 1/2/21
 * description: 使用startDrag实现视图和数据的拖拽，它的数据可跨进程传递
 */
class DragToDestinationLayout(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val mLongClickListener = OnLongClickListener { v ->
        //封装传输的数据
        val imageData = ClipData.newPlainText("name", v.contentDescription)
        //data: 跨进程拖拽时传输的数据 localState: 应用内的数据
        // DragShadowBuilder：拖拽时view，它是一坨像素被绘制在最上层，所以不会被盖住
        ViewCompat.startDragAndDrop(v, imageData, DragShadowBuilder(v), null, 0)
    }

    private val mDragListener = DestinationListener()

    override fun onFinishInflate() {
        super.onFinishInflate()
        avatarView.setOnLongClickListener(mLongClickListener)
        destinationLayout.setOnDragListener(mDragListener)
    }

    inner class DestinationListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DROP -> if (v is LinearLayout) {
                    val textView = TextView(context)
                    textView.textSize = 16f
                    textView.text = event.clipData.getItemAt(0).text
                    v.addView(textView)
                }
            }
            return true
        }
    }

}