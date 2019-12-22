package com.zzj.core.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RespData implements Serializable {
    private Long id;
    private Object result;
    private Class resultType;
    private boolean success;
    private String msg;
}
