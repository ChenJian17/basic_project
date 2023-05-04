package com.hxh.basic.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @Author: ChenJian
 * @Date: 2021/5/25 15:13
 */
@Slf4j
@RestController
@RequestMapping("/logging")
public class LogController {

    @GetMapping("/do")
    public String log() {
        log.info("log4j2 test date: {}  info: {}", LocalDate.now(), "请关注公众号：Felordcn");
        return "log4j2";
    }
}
