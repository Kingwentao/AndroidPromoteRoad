package com.example.androidpromoteroad.kotlinupgrade

import android.app.Activity
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.androidpromoteroad.R
import kotlinx.android.synthetic.main.activity_upgrade.*
import kotlin.reflect.KProperty

fun Activity.log(info: String) {
    Log.d("TAG", "activity : $info")
}

class UpgradeActivity : AppCompatActivity(), BaseView<UpgradePresenter> {

    //实现抽象属性+代理懒加载
    override val presenter: UpgradePresenter by lazy {
        UpgradePresenter()
    }

    //把token的set和get操作委托给Saver类
    var token: String by Saver("token")

    //apply: 对象附加操作,返回值是它本身
    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upgrade)
        log("i am upgrade activity")

        //let 和 also 的不同在于返回值
        val res1 = presenter.let {

        }

        val res2 = presenter.also {

        }
        //run 把代码包成单元块
        findViewById<TextView>(R.id.tvUpgradeTest).run {

        }
    }

    class Saver(val token: String) {
        operator fun getValue(upgradeActivity: UpgradeActivity, property: KProperty<*>): String {
            return "test token"
        }

        operator fun setValue(upgradeActivity: UpgradeActivity, property: KProperty<*>, s: String) {
            //
        }

    }

}