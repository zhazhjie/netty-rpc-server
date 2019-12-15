package com.zzj.core;

import com.zzj.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String sayHi(String name) {
        return "hi,"+name;
    }
}
