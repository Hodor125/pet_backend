package com.hodor.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {

        return "ok";
    }
}
