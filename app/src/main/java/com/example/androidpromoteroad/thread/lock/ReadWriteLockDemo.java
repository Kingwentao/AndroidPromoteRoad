package com.example.androidpromoteroad.thread.lock;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * author: WentaoKing
 * created on: 7/10/21
 * description: 读写锁的使用
 * 所谓读写锁，是对访问资源共享锁和排斥锁
 * 1.如果对资源加了写锁，其他线程无法再获得写锁与读锁，但是持有写锁的线程，可以对资源加读锁（锁降级）
 * 2.如果一个线程对资源加了读锁，其他线程可以继续加读锁。
 */
class ReadWriteLockDemo {

    private int x = 0;
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    // 读和写的过程使用不同的锁
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) { }

    private void print() {
        readLock.lock();
        try {
            System.out.println("x: " + x);
        } finally {
            readLock.unlock();
        }
    }

    private void write() {
        writeLock.lock();
        try {
            x++;
        } finally {
            writeLock.unlock();
        }
    }

}
