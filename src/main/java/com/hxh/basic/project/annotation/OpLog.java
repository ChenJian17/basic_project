package com.hxh.basic.project.annotation;

import com.hxh.basic.project.enums.OperationType;

import java.lang.annotation.*;

/**
 * @Author: ChenJian
 * @Date: 2021/5/24 15:47
 */

/** 这个注解将作用于方法，并且在运行时有效
 * @Target 注解描述了这个注解可以被用到的地方, 如注解可以被用于包上, 类上或者成员属性或成员方法上, ElementType.METHOD可以给方法进行注解
 * @Retention 注解描述了一个注解的存活时间,RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候,
 * 会被加载进入到 JVM 中, 在程序运行时可以获取到它们
 * @Documented 用于文档的生成, 能够将注解中的元素包含到 Javadoc 中去
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {
    /**
     * 方法描述,可使用占位符获取参数:{{tel}}
     */
    String value() default "";

    /**
     * 日志等级:自己定，此处分为1-9
     */
    int level() default 4;

    /**
     * 操作类型(enum):主要是select,insert,update,delete
     */
    OperationType operationType() default OperationType.UNKNOWN;

    /**
     * 被操作的对象(此处使用enum):可以是任何对象，如表名(user)，或者是工具(redis)
     */
    String operationUnit() default "unknown";
}
