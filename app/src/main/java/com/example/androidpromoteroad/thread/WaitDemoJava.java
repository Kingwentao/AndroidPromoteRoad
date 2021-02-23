package com.example.androidpromoteroad.thread;

/**
 * author: created by wentaoKing
 * date: created in 1/17/21
 * description: wait demo of java code version
 */
class WaitDemoJava {

    private String mShareString = "";

    private void printShareString() {
//        try {
//            wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("print: " + mShareString);
    }

    private void setShareString(String s) {
        mShareString = s;
//        notifyAll();
    }

    void runTest() {
        System.out.println("run");
        //开启一个线程去设置共享的值，假设2s后才能完成
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //... TODO收尾工作
                }
                setShareString("i am set value thread");
            }
        });
        thread1.start();

        //开启一个线程去打印共享的值，假设1s就可以读值
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //如果设置join的时间100ms，那么100ms后，无论join的线程任务是否执行完成，该线程的任务都会继续下去
                    //thread1.join(100);
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printShareString();
            }
        });
        thread2.start();
    }
}
