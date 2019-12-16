package com.zzj.core;

import com.zzj.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {
    @Bean
    public UserService userService(){
        return ProxyFactory.build(UserService.class);
    }
}
