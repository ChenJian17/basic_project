package com.hxh.basic.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ChenJian
 * @Date: 2021/5/19 14:31
 */
@RestController
@RequestMapping("/rule")
public class RuleController {
    @Autowired
    private JdbcTemplate jdbcTemplate;



}
