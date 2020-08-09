package com.example.androidpromoteroad.architecture.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpromoteroad.R
import kotlinx.android.synthetic.main.activity_mvvm.*

/**
 * MVVM的例子: 自己实现一个简易的数据双向绑定
 */
class MvvmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)

        //dataBinding 是用来做MVVM的，但ViewModel不是用来做MVVM的，Google只是用它来做数据存储......
        ViewModel(etMvvm).init()
    }
}