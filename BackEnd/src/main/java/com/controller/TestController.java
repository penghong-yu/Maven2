package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yu
 * @title: TestController
 * @projectName demo
 * @description: TODO
 * @date 2021/5/31/21:12
 */

@RestController
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "test myController";
    }
}
