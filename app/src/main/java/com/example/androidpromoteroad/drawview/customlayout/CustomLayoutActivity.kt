package com.example.androidpromoteroad.drawview.customlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CustomLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = TheLayout(this)
        setContentView(layout)
    }
}