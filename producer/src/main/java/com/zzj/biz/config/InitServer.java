package com.zzj.biz.config;

import com.zzj.core.common.utils.BeanFactory;
import com.zzj.core.producer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Component
public class InitServer implements InitializingBean {
    @Autowired
    ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception{
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(Service.class);
        BeanFactory.setBean(beansWithAnnotation);
        new Server().start();
        log.info("server init!");
    }
}
