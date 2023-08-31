package com.example.Result;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private Integer statusCode;

    private String msg;

    private T data;

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }
}