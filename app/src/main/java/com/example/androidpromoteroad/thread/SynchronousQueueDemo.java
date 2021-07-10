package com.example.androidpromoteroad.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * author: WentaoKing
 * created on: 7/8/21
 * description: SynchronousQueueDemo的使用
 * 生产者往 SynchronousQueue 中插入一个元素之后，生产者线程会等待消费者完成消费，
 * 而消费者线程在完成消费之后会等待生产者生产
 */

class SynchronousQueueDemo {

    //SynchronousQueue的公平特性
    private static final BlockingQueue<Integer> queue = new SynchronousQueue<>(true);

    private static class Producer implements Runnable {
        @Override
        public void run() {
            int count = 0;
            while (count < 10) {
                int val = count++;
                System.out.println("Producer produce: " + val);
                try {
                    queue.put(val);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Consumer implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Consumer " + Thread.currentThread().getName()
                            + " consume: " + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread producer = new Thread(new Producer());
        Thread consumer1 = new Thread(new Consumer());
        Thread consumer2 = new Thread(new Consumer());
        //SynchronousQueue 的公平性特性尽可能保证了消费者 A 和 B 能够交替执行消费操作。
        consumer1.setName("A");
        consumer2.setName("B");
        producer.start();
        consumer1.start();
        consumer2.start();
    }

}
