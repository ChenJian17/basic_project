package com.hxh.basic.project;

import java.util.concurrent.TimeUnit;

/**
 * @Author: ChenJian
 * @Date: 2021/6/2 19:45
 */
public class MyRunnable implements Runnable{
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + "-------------进入");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(currentThread.getName() + "-------------离开");
        }
    }
}
