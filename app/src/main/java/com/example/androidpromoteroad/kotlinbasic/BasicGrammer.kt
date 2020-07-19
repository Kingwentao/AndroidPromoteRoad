package com.example.androidpromoteroad.kotlinbasic

/**
 * author: created by wentaoKing
 * date: created in 2020-07-16
 * description: kotlin基础语法
 */
class BasicGrammer {

    fun array() {

        //1. 会自动装箱，影响性能
        val arrayOf = arrayOf(1, 2, 3)
        //2. 不会装箱，性能较好
        val arrayOf2 = intArrayOf(1, 2, 3)

        /**
         *  java和kotlin类型对应
         *  java  kotlin
         *  Int   Int?
         *  Long  Long?
         *  int   Int
         *  long  Long
         */

        /**
         * internal 修饰符某个类，让其只可在模块内访问
         *
         * kotlin因为有了val，所以final只用于类和方法，默认存在
         */
    }

}