package com.zzj.biz.service;

import com.zzj.common.entity.User;
import com.zzj.common.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String sayHi(String name) {
        return "hi,"+name;
    }

    @Override
    public User getUserById(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("aaa");
        user.setAge(99);
        return user;
    }

    @Override
    public void doSomething() {
        System.out.println("do something");
    }
}
