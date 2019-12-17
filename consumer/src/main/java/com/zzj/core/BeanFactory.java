package com.zzj.core;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory<T> {
    private Map<Class<T>,Object> beanMap=new HashMap<Class<T>,Object>(){
        {

        }
    };

}
