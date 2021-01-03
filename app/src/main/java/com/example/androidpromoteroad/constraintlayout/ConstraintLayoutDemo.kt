package com.example.androidpromoteroad.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.androidpromoteroad.R
import kotlinx.android.synthetic.main.activity_constraint_helper.*

class ConstraintLayoutDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout_demo)
        //group.visibility = View.GONE

        avatar.setOnClickListener {
            placeHolder.setContentId(it.id)
        }

        //setContentView(R.layout.activity_constraint_helper)
    }

    fun onClick(view: View) {
        //通过ConstraintSet()可实现整个xml布局的替换  bugfix：强转会崩溃
        val constraintLayout = view as ConstraintLayout
        val constraintSet = ConstraintSet().apply {
            isForceId = false
            clone(
                this@ConstraintLayoutDemo,
                R.layout.activity_constraint_helper2
            )
        }
        TransitionManager.beginDelayedTransition(constraintLayout)
        constraintSet.applyTo(constraintRoot)
    }
}