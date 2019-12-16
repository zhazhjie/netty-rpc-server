package com.zzj.core;

import com.zzj.service.UserService;
import entity.User;

public class UserServiceImpl implements UserService {
    @Override
    public String sayHi(String name) {
        return "hi,"+name;
    }

    @Override
    public User getUserById(Long id) {
        User user = new User();
        user.setId(1L);
        user.setName("jac");
        user.setAge(99);
        return user;
    }
}
