package com.hxh.basic.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: ChenJian
 * @Date: 2021/3/21 14:33
 */
public class Test {
    public static void main(String[] args) {
//       String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        System.out.println(date);

        Calendar calendar2 = Calendar.getInstance();
        System.out.println(calendar2.toString());
        System.out.println("===============");
        System.out.println(calendar2.getClass().getName());
        System.out.println("========反射==========");
        Class<? extends Calendar> class1 = calendar2.getClass();
        System.out.println(class1.hashCode());

    }
}
