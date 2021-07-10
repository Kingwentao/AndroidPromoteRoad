package com.example.androidpromoteroad.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * author: created by wentaoKing
 * date: created in 1/17/21
 * description:
 */
public class MainJava {

    public static void main(String[] args) {
        System.out.println("java main");
       // new WaitDemoJava().runTest();

        //处理爆发式的任务
        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "Call Down!";
            }
        };

        Future<String> future =  executorService.submit(callable);
        try {
            String result = future.get();
            System.out.println("result: " + result);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
