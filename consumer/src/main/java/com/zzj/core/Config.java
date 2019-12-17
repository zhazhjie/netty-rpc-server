package com.zzj.core;

import com.zzj.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public UserService userService(){
        return ProxyFactory.build(UserService.class);
    }
}
