package com.example.androidpromoteroad.kotlinupgrade

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue

/**
 * author: WentaoKing
 * created on: 2020/7/22
 * description: kotlin进阶语法
 */
val user = User("a", "b")
val copyUser = user.copy()
private const val TAG = "upgrade_grammer"

fun main() {
    println(copyUser == user)
    println(copyUser === user)

    //扩展函数
    val extendFun = 2F.dp2px()

    //内联函数

    log("main")

    //2. 函数类型使用
    val view = View()
    //2.1 用::传递声明函数
    view.setOnClickListener(::onClick)
    //2.2 匿名传递函数
    view.setOnClickListener(fun(view: View) {
        println("$view 被点击了...")
    })
    //2.3 lamda传递函数
    view.setOnClickListener {
        println("$it 被点击了...")
    }
}

inline fun log(info: String) {
    Log.d("inline", "i am inline function --< $info")
}

fun Float.dp2px(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
}

class View {

    //函数类型
    fun setOnClickListener(listener: (View) -> Unit) {
        Log.d(TAG, "setOnClickListener")
    }

}

fun onClick(view: View) {
    println("$view 被点击了...")
}