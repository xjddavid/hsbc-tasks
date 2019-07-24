package com.jiang.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaskController {
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "hello world";
    }
}
