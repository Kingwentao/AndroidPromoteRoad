package com.example.androidpromoteroad.leakcanary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpromoteroad.R

class LeakCanaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_cancary)
        //LeakCanary的使用方法很简单，见MyApplication
    }
}