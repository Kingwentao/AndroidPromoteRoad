package com.example.androidpromoteroad.kotlinbasic

/**
 * author: created by wentaoKing
 * date: created in 2020-07-16
 * description: 类似java实现 bean的使用
 */
class User {

    //此注解能让该变量变成成员变量，不会生产get和set方法
    @JvmField
    var userName: String? = null

    var userPassword: String? = null
        set(value) {
            field = value
        }
        get() {
            return field
        }
}