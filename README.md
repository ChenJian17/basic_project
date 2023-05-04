## 前言
* 这个项目是我想快速搭建Springboot框架而使用的一个模板，主要是基于[basic_project](https://gitee.com/huangxunhui/basic_project.git)
这个开源项目，这个开源项目做了各种分层和封装，但也缺少了许多集成和功能，所以我增加了如下一些功能。

## 功能
* 使用了lombook，log4j2，引入pom文件
* 在filter层下增加了个JWT过滤器。
* 实现HandlerInterceptor接口的拦截器
* 增加基于注解的AOP日志切面
* 增加了mybatis-plus的代码生成器，可以运行基于数据库自动生成各层代码
* 引入redis，在util层封装了redis各种常用操作的代码
* 封装okhttp的util

## 待增加
* 多租户的数据源切换的代码
* 简易读写分离