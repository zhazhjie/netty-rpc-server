package com.zzj.service;

import com.zzj.entity.User;

public interface UserService {
    String sayHi(String name);
    User getUserById(Long id);
    void doSomething();
}
