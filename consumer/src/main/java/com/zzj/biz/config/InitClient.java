package com.zzj.biz.config;

import com.zzj.core.consumer.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitClient implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception{
        new Client().start("127.0.0.1", 8888);
        log.info("client init!");
    }
}
