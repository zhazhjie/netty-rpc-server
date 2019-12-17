package com.zzj.exception;

public class TimeoutException extends RuntimeException{
    private int code=500;
    private String msg;
    public TimeoutException(String msg){
        super(msg);
        this.msg=msg;
    }
    public TimeoutException(int code,String msg){
        this(msg);
        this.code=code;
    }
    public TimeoutException(int code,String msg,Throwable e){
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
