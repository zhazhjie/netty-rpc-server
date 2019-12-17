package com.zzj.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitClient implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception{
        new Client().start();
        log.info("init");
    }
}
