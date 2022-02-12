package com.example.androidpromoteroad.aop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.androidpromoteroad.R
import kotlinx.android.synthetic.main.activity_aop.*

class AopActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "AopActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop)
        btnFastClick.setOnClickListener(object: View.OnClickListener{
            @FastClickView(2000L)
            override fun onClick(view: View?) {
                Log.d(TAG, "onClick: click me...")
            }
        })
    }
}