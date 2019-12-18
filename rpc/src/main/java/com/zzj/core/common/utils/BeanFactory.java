package com.zzj.core.common.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private static Map<Class, Object> beanMap = new HashMap<>();

    public static void setBean(Map<String, Object> beanStringMap) {
        beanStringMap.forEach((key, bean) -> {
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            Arrays.stream(interfaces).forEach(item->{
                beanMap.put(item,bean);
            });
            beanMap.put(bean.getClass(), bean);
        });
    }

    public static void setBean(Class key, Object value) {
        beanMap.put(key, value);
    }

    public static Object getBean(Class clazz) {
        return beanMap.get(clazz);
    }

    ;
}
