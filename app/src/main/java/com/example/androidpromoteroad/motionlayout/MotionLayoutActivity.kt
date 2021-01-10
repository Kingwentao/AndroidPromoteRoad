package com.example.androidpromoteroad.motionlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androidpromoteroad.R
import kotlinx.android.synthetic.main.activity_motion_layout.*

class MotionLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)
    }

    private fun doAnimationByLayoutParams() {
        TransitionManager.beginDelayedTransition(csRoot)
        with(ivAvatar.layoutParams as ConstraintLayout.LayoutParams) {
            width *= 2
            height *= 2
        }
        ivAvatar.requestLayout()
    }

    fun onClick(v: View) {
        //doAnimationByLayoutParams()
        when (v.id) {
            R.id.btnPrePage -> {
                val intent = Intent(this, MotionLayoutActivity3::class.java)
                startActivity(intent)
            }
            R.id.btnNextPage -> {
                val intent = Intent(this, MontionLayoutActivity2::class.java)
                startActivity(intent)
            }
        }

    }
}