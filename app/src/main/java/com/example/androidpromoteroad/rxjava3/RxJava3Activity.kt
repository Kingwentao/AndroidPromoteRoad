package com.example.androidpromoteroad.rxjava3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpromoteroad.R
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rx_java3.*

class RxJava3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java3)

        val single = Single.just("1")
        single.subscribe(object: SingleObserver<String>{
            override fun onSuccess(t: String?) {
                tvRxjava3.text = "$t 请求成功"
            }

            override fun onSubscribe(d: Disposable?) {
                tvRxjava3.text = "开始请求"
            }

            override fun onError(e: Throwable?) {
                tvRxjava3.text = "请求错误 $e"
            }

        })

    }
}