package com.example.androidpromoteroad.generic.kotlingenerice

/**
 * author: created by wentaoKing
 * date: created in 2020/9/20
 * description:
 */
class KotlinGenericMain {
    fun main(){

        val outShop: KotlinShop<out KotlinApple> = KotlinShop()
        val inShop: KotlinShop<in KotlinApple> = KotlinShop()

        val outSale = outShop.sale()  // 返回 KotlinApple
        val inSale = inShop.sale()         //  返回 Any

        //outShop.buy(KotlinApple()) //编译报错
        inShop.buy(KotlinApple())   //编译通过
     }
}