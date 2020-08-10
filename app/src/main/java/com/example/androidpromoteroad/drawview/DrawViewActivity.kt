package com.example.androidpromoteroad.drawview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpromoteroad.R
import kotlinx.android.synthetic.main.activity_draw_view.*

class DrawViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_view)
        var markNum = 0
        dashView.setOnClickListener {
            dashView.markNum = markNum++
            dashView.invalidate()
        }
    }
}