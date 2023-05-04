package com.hxh.basic.project.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Author: ChenJian
 * @Date: 2021/5/24 15:45
 */

@Aspect
@Component
public class LoggerAspect {

    //Advice的类型主要有before,after,around;around是最常用的 advice，意为在代码前后都执行
    //下面的before和after合在一起相当于around
    /**
     * addUser()执行之前执行
     */
    @Before("execution(* com.hxh.basic.project.controller.UserController.addUser(..))")
    public void callBefore() {
        System.out.println("before call method");
        System.out.println("begin........................");
    }

    /**
     * addUser()执行之后执行
     */
    @After("execution(* com.hxh.basic.project.controller.UserController.addUser(..))")
    public void callAfter() {
        System.out.println("after call method");
        System.out.println("end..............................");
    }

    //=============================使用了Around===========================================
    /**
     * 记录执行时间
     * @param point 切点
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.hxh.basic.project.controller.UserController.addUser(..))")
    public Object getMethodExecuteTime(ProceedingJoinPoint point) throws Throwable {
        System.out.println("---------------getMethodExecuteTime------------------");
        long startTime = System.currentTimeMillis();
        //这句代码是调用目标方法
        Object result = point.proceed();
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        System.out.println("executeTime=" + executeTime + "------------------");

        return result;
    }

    //=============================使用了自定义注解===========================================
    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     */
    @Pointcut("@annotation(com.hxh.basic.project.annotation.OpLog)")
    public void operationLog(){

    }

    /**
     * @param point
     * @return
     * @throws Throwable
     */
//    @Around("operationLog()")
    @Around("@annotation(com.hxh.basic.project.annotation.OpLog)")
    public Object getMethodExecuteTimeForLogger(ProceedingJoinPoint point) throws Throwable {
        System.out.println("---------------getMethodExecuteTime------------------");
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        System.out.println("executeTime=" + executeTime + "------------------");

        return result;
    }

}
