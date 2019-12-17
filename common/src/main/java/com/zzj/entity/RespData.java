package com.zzj.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RespData implements Serializable {
    private Long id;
    private String result;
    private Class resultType;
    private boolean success=true;
    private String msg="success";
}
