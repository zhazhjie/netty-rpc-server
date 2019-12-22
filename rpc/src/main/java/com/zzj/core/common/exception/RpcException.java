package com.zzj.core.common.exception;

public class RpcException extends RuntimeException{
    private Long reqId;
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
    public RpcException(Long reqId,String msg){
        this(msg);
        this.reqId=reqId;
    }
    public RpcException(Long reqId,int code,String msg){
        this(code,msg);
        this.reqId=reqId;
    }
    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
