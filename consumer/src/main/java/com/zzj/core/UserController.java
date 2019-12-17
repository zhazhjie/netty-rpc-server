package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.entity.ReqData;
import com.zzj.entity.User;
import com.zzj.service.UserService;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public User getUser(){
        log.info("getUser");
        User userById = userService.getUserById(1L);
        return userById;
    }
    @GetMapping("/sayHi")
    public String sayHi(){
        System.out.println("hi");
        String jac = userService.sayHi("jac");
        return jac;
    }

    @GetMapping("/doSomething")
    public String doSomething(){
        System.out.println("doSomething");
        userService.doSomething();
        return "do";
    }
}
