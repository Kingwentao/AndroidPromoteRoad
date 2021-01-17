package com.example.androidpromoteroad.thread

import okhttp3.internal.notifyAll
import okhttp3.internal.wait

/**
 * author: created by wentaoKing
 * date: created in 1/17/21
 * description: wait demo of kotlin code
 */
class WaitDemo {

    private var mShareString = ""

    @Synchronized
    private fun printShareString(){
        try {
            wait()
        }catch (e: InterruptedException){

        }
        println("print: $mShareString")
    }

    @Synchronized
    private fun setShareString(s: String){
        mShareString = s
        notifyAll()
    }

    fun runTest(){
        //开启一个线程去设置共享的值，假设2s后才能完成
        val thread1 = Thread{
            try {
                Thread.sleep(2000)
            }catch (e: InterruptedException){
                //... TODO收尾工作
            }
            setShareString("i am set value thread")
        }
        thread1.start()

        //开启一个线程去打印共享的值，假设1s就可以读值
        val thread2 = Thread{
            printShareString()
        }
        thread2.start()
    }
}