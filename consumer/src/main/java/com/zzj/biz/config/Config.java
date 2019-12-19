package com.zzj.biz.config;

import com.zzj.common.service.UserService;
import com.zzj.core.common.utils.ProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public UserService userService(){
        return ProxyFactory.build(UserService.class);
    }
}
