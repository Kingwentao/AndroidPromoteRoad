package com.example.androidpromoteroad.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

/**
 * author: WentaoKing
 * created on: 2/22/21
 * description: 按顺序运行多个线程，例如将T1、T2、T3三个线程顺序执行
 */
class SequenceRunThread {

    public static void main(String[] args) {
//        joinThread();
//        countDownLatch();
//        futureTask();
//        singleExecutor();
        waitAndNotify();
    }

    //方法1： 使用join方法，把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。直到加入的线程执行完成后才执行当前线程
    private static void joinThread() {
        Thread t1 = new Thread(new WorkThread(null));
        Thread t2 = new Thread(new WorkThread(t1));
        Thread t3 = new Thread(new WorkThread(t2));
        t1.start();
        t2.start();
        t3.start();
    }

    //方法2：利用CountDownLatch(闭锁)我们可以拦截一个或多个线程使其在某个条件成熟后再执行
    private static void countDownLatch() {
        CountDownLatch c0 = new CountDownLatch(0); //计数器为0
        CountDownLatch c1 = new CountDownLatch(1); //计数器为1
        CountDownLatch c2 = new CountDownLatch(1); //计数器为1

        Thread t1 = new Thread(new CountDownRunnable(c0, c1));
        Thread t2 = new Thread(new CountDownRunnable(c1, c2));
        Thread t3 = new Thread(new CountDownRunnable(c2, c2));
        t1.start();
        t2.start();
        t3.start();
    }

    //方法3：FutureTask一个可取消的异步操作，可以查询操作是否已经完成，并且可以获取计算的结果。
    // 结果只可以在计算完成之后获取，get方法会阻塞当计算没有完成的时候
    private static void futureTask() {
        FutureTask<Integer> future1 = new FutureTask(new FutureTaskCallable(null));
        FutureTask<Integer> future2 = new FutureTask(new FutureTaskCallable(future1));
        FutureTask<Integer> future3 = new FutureTask(new FutureTaskCallable(future2));
        Thread t1 = new Thread(future1);
        Thread t2 = new Thread(future2);
        Thread t3 = new Thread(future3);
        t1.start();
        t2.start();
        t3.start();
    }

    //方法4：使用单一线程池
    private static void singleExecutor() {
        Thread t1 = new Thread(new NormalRunnable("T1"));
        Thread t2 = new Thread(new NormalRunnable("T2"));
        Thread t3 = new Thread(new NormalRunnable("T3"));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(t3);
        executor.submit(t2);
        executor.submit(t1);
        executor.shutdown();
    }


    static Boolean t1IsRun = false;

    //方法5：使用wait/notify方法
    private static void waitAndNotify(){

        Object myLock1 = new Object();
        Object myLock2 = new Object();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (myLock1){
                    t1IsRun = true;
                    System.out.println("产品经理规划新需求...");
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //2.唤醒持有该锁的线程任务
                    myLock1.notify();
                }
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (myLock1){
                    try {
                        if(!t1IsRun){
                            System.out.println("等待产品经理规划...");
                            //1.释放锁，该线程堵塞在此处开始等待唤醒
                            myLock1.wait();
                        }
                        //3.继续执行
                        synchronized (myLock2){
                            System.out.println("开发人员开发新需求功能");
                            myLock2.notify();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread2.start();
        thread1.start();
    }

    static class FutureTaskCallable implements Callable<Integer> {
        private FutureTask<Integer> beforeFutureTask;

        FutureTaskCallable(FutureTask<Integer> beforeFutureTask) {
            this.beforeFutureTask = beforeFutureTask;
        }

        @Override
        public Integer call() throws Exception {
            if (beforeFutureTask != null) {
                Integer result = beforeFutureTask.get(); //阻塞等待，结果会在完成之后获取
                System.out.println("thread start:" + Thread.currentThread().getName() + "res: " + result);
                sleep(3000);
            } else {
                System.out.println("thread start:" + Thread.currentThread().getName());
                sleep(1000);
            }
            return 0;
        }
    }

    static class WorkThread implements Runnable {
        private Thread beforeThread;

        public WorkThread(Thread beforeThread) {
            this.beforeThread = beforeThread;
        }

        public void run() {
            if (beforeThread != null) {
                try {
                    //等待beforeThread执行完才执行当前线程任务
                    beforeThread.join();
                    System.out.println("thread start:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("thread start:" + Thread.currentThread().getName());
            }
        }
    }

    static class CountDownRunnable implements Runnable {
        CountDownLatch c1;
        CountDownLatch c2;

        /**
         * 需要传入开始和结束的的CountDownLatch
         */
        CountDownRunnable(CountDownLatch c1, CountDownLatch c2) {
            super();
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        public void run() {
            try {
                //只有当count为0时，await之后的程序才够执行
                c1.await();
                System.out.println("thread start:" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //countDown必须写在finally中，防止发生异程常时，导致程序死锁。
                c2.countDown();
            }
        }
    }

    static class NormalRunnable implements Runnable {
        private String name;

        NormalRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + "thread is running");
        }
    }

}