package com.zzj.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ReqData implements Serializable {
    private Long id;
    private Class targetType;
    private String methodName;
    private Object[] args;
    private Class[] argsType;
}