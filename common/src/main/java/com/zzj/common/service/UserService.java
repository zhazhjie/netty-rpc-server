package com.zzj.common.service;

import com.zzj.common.entity.User;

public interface UserService {
    String sayHi(String name);
    User getUserById(Long id);
    void doSomething();
}
