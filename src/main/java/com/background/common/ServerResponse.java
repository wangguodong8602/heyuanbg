package com.background.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候，如果是null的对象，key也会消失
public class ServerResponse<T> implements Serializable {

    private int code;
    private String msg;
    private int count;
    private T data;

    private ServerResponse(int code){
        this.code = code;
    }
    private ServerResponse(int code,T data){
        this.code = code;
        this.data = data;
    }
    private ServerResponse(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    private ServerResponse(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
    private ServerResponse(int code,String msg,int count,T data){
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.code == ResponseCode.SUCCESS.getCode();
    }
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
    public int getCount(){return  count;}
    public T getData(){
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg,int count,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,count,data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }

}
