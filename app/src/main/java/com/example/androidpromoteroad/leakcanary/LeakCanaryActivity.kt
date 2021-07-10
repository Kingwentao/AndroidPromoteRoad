package com.example.androidpromoteroad.leakcanary

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpromoteroad.R
import kotlinx.android.synthetic.main.activity_leak_cancary.*

class LeakCanaryActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LeakCanaryActivity"
    }

    inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "handle message $msg")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_cancary)
        //LeakCanary的使用方法很简单，见MyApplication
        //MyHandler().sendEmptyMessageDelayed(0, 20000)
        MyThread().start()
        btnLeak.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    inner class MyThread : Thread() {
        override fun run() {
            Log.d(TAG, "run start")
            super.run()
            sleep(10000)
            Log.d(TAG, "run end")
        }
    }

}