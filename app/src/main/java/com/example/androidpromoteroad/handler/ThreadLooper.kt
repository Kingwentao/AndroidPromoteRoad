package com.example.androidpromoteroad.handler

import android.os.Looper
import android.os.MessageQueue

/**
 * author: WentaoKing
 * created on: 8/25/21
 * description: 将子线程的任务放到主线程的消息队列中
 * 使用线程创建 UI 的时候，先把线程的 Looper 的 MessageQueue 替换成 UI 线程 Looper 的 Queue。
 * 需要注意的是，在创建完 View 后我们需要把线程的 Looper 恢复成原来的。
 */
class ThreadLooper {

//    fun prepareLooperWithMainThreadQueue(reset: Boolean): Boolean {
//        if (isMainThread()) {
//            return true
//        } else {
//            val threadLocal: ThreadLocal<Looper> =
//                ReflectionHelper.getStaticFieldValue(Looper::class.java, "sThreadLocal")
//                    ?: return false
//            var looper: Looper? = null
//            if (!reset) {
//                Looper.prepare();
//                looper = Looper.myLooper();
//                val queue = ReflectionHelper.invokeMethod(
//                    Looper.getMainLooper(),
//                    "getQueue",
//                    Class[0],
//                    Any[0]
//                )
//                if (queue !is MessageQueue) {
//                    return false
//                }
//                looper?.let {
//                    ReflectionHelper.setFieldValue(looper, "mQueue", queue)
//                }
//            }
//            ReflectionHelper.invokeMethod(threadLocal, "set", Class[]{}, Any[]{ looper })
//            return true
//        }
//    }

    private fun isMainThread(): Boolean {
        return false
    }
}