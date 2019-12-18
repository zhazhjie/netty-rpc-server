package com.zzj.biz.controller;

import com.zzj.common.entity.User;
import com.zzj.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public User getUser(HttpServletRequest request){
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
