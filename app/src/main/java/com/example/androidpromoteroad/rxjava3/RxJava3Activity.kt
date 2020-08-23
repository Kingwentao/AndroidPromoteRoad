package com.example.androidpromoteroad.rxjava3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpromoteroad.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import kotlinx.android.synthetic.main.activity_rx_java3.*
import java.util.concurrent.TimeUnit

class RxJava3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java3)

//        val single = Single.just(1)
//        //map 操作符
//        val singleString = single.map(object : Function<Int,String>{
//            override fun apply(t: Int?): String {
//                return t.toString()
//            }
//        })
//
//        singleString.subscribe(object: SingleObserver<String>{
//            override fun onSuccess(t: String?) {
//                tvRxjava3.text = "$t 请求成功"
//            }
//
//            override fun onSubscribe(d: Disposable?) {
//                tvRxjava3.text = "开始请求"
//            }
//
//            override fun onError(e: Throwable?) {
//                tvRxjava3.text = "请求错误 $e"
//            }
//
//        })

        //interval 间隔操作符
        Observable.interval(0,1,TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: Long?) {
                    tvRxjava3.text = t.toString()
                }

                override fun onError(e: Throwable?) {
                }

            })

    }
}