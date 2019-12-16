package com.zzj.service;

import entity.User;

public interface UserService {
    String sayHi(String name);
    User getUserById(Long id);
}
