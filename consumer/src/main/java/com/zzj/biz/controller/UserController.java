package com.zzj.biz.controller;

import com.zzj.common.entity.User;
import com.zzj.common.service.UserService;
import com.zzj.core.consumer.ChannelConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public User getUser(Long id) {
        log.info("getUser");
        return userService.getUserById(id);
    }

    @GetMapping("/sayHi")
    public String sayHi(String name) {
        log.info("sayHi");
        return userService.sayHi(name);
    }

    @GetMapping("/doSomething")
    public String doSomething() {
        log.info("doSomething");
        userService.doSomething();
        return "do";
    }
}
