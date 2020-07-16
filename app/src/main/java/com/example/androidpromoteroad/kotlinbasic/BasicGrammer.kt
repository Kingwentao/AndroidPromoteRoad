package com.example.androidpromoteroad.kotlinbasic

/**
 * author: created by wentaoKing
 * date: created in 2020-07-16
 * description: kotlin基础语法
 */
class BasicGrammer {

    fun array(){

        //1. 会自动装箱，影响性能
        val arrayOf = arrayOf(1,2,3)
        //2. 不会装箱，性能较好
        val arrayOf2 = intArrayOf(1,2,3)
    }

}