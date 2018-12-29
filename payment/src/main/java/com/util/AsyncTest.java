package com.util;

import java.util.concurrent.CountDownLatch;

/**
 * Created by wufuming on 2018/11/14.
 */
public class AsyncTest implements Runnable{
    private int a;
    private final CountDownLatch countDown;

    AsyncTest(CountDownLatch countDown,int a){
        this.countDown = countDown;
        this.a= a;
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ",初始变量a:" + a);
        a = a + 1;
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){

        }
        System.out.println(Thread.currentThread().getName() + ",计算后变量a:" + a);
        a = a + 1;
        System.out.println(Thread.currentThread().getName() + ",最终变量a:" + a);
        countDown.countDown();
    }
}
