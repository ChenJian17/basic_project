package com.hxh.basic.project;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: ChenJian
 * @Date: 2021/6/2 19:46
 */
public class MyTest {
//    public static void main(String[] args) {
//        MyRunnable myRunnable = new MyRunnable();
//        Thread thread1 = new Thread(myRunnable, "线程1");
//        Thread thread2 = new Thread(myRunnable, "线程2");
//        Thread thread3 = new Thread(myRunnable, "线程3");
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//    }

//    public static void main(String[] args) throws Exception {
//
//        String test = "begin";
//        System.out.println(test);
//
//        //线程1
//        new Thread("Thread1-Name") {
//            @Override
//            public void run() {
//                System.out.println("Thread1-Name Start");//断点处
//                try {
//                    Thread.sleep(1000);
////                    decryptByPublicKey();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Thread1-Name end");
//
//            }
//        }.start();
//        //线程2
//        new Thread("Thread2-Name") {
//            @Override
//            public void run() {
//                System.out.println("Thread2-Name Start");//断点处
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Thread2-Name end");
//            }
//
//        }.start();
//
//
//        System.out.println("end");//断点处
//    }

    public static void main(String[] args) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(30);//线程池创建时候初始化的线程数
        executor.setMaxPoolSize(60);//线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setQueueCapacity(1500);//用来缓冲执行任务的队列
        executor.setKeepAliveSeconds(60);//当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setThreadNamePrefix("msgcenter-cloud-taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//采用CallerRunsPolicy策略
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(index);
                }
            });
        }
    }

}
