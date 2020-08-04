package com.example.androidpromoteroad.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpromoteroad.R
import okhttp3.*
import java.io.IOException

class OkHttpMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http_main)

        val url = "https://api.github.com/users/rengwuxian/repos"
        val hostname = "api.github.com"

        val client = OkHttpClient.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("Response status code: ${response.code}")
                }
            })
    }
}