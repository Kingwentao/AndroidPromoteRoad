package com.example.androidpromoteroad.thread

import okhttp3.internal.notifyAll
import okhttp3.internal.wait

/**
 * author: created by wentaoKing
 * date: created in 1/17/21
 * description: wait demo of kotlin code
 *
 * 使用注意：
 * 1. wait 和 notify/notifyAll 必须成对出现
 * 2. wait 和 notify 方法是 monitor 的调用，所以要在同步代码块/方法里面调用
 * 3. notify 可能会导致死锁，而 notifyAll 则不会
 *
 * wait和notify的解释：
 * 1.如果线程调用了对象的 wait()方法，那么线程便会处于该对象的等待池中，等待池中的线程不会去竞争该对象的锁。
 * 2.当有线程调用了对象的 notifyAll() 或 notify() 方法（只随机唤醒一个 wait 线程），
 *   被唤醒的的线程便会进入该对象的锁池中，锁池中的线程会去竞争该对象锁。
 *   也就是说，调用了 notify 后只要一个线程会由等待池进入锁池，而 notifyAll 会将该对象等待池内的所有线程移动到锁池中，等待锁竞争。
 *
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
        //比notify更靠谱，可能notify没有唤醒并不是需要的那一个
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