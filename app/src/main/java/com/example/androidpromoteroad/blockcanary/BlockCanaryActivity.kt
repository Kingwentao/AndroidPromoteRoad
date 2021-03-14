package com.example.androidpromoteroad.blockcanary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.os.Trace
import com.example.androidpromoteroad.R
import kotlinx.android.synthetic.main.activity_block_canary.*

class BlockCanaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_canary)
        //记录
        Trace.beginSection("onCreate")
        btnCheck.setOnClickListener {
            useTimeA()
            useTimeB()
            useTimeC()
//          SystemClock.sleep(1000)
        }
        Trace.endSection()
    }

    private fun useTimeA() {
        SystemClock.sleep(1000)
        println()
    }

    private fun useTimeB() {
        SystemClock.sleep(15)
        println()
    }

    private fun useTimeC() {
        SystemClock.sleep(2000)
        println()
    }
}