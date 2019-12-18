package com.zzj.core.common.exception;

public class RpcException extends RuntimeException{
    private int code=500;
    private String msg;
    public RpcException(String msg){
        super(msg);
        this.msg=msg;
    }
    public RpcException(int code,String msg){
        this(msg);
        this.code=code;
    }
    public RpcException(int code,String msg,Throwable e){
        super(msg,e);
        this.code=code;
        this.msg=msg;
    }
    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
