package com.zzj.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReqData {
    private Long id;
    private Class targetType;
    private String methodName;
    private Object[] args;
    private Class[] argsType;
}
