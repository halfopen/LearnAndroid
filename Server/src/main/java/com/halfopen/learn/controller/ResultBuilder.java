package com.halfopen.learn.controller;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by h on 2017/8/8.
 */
public class ResultBuilder{
    private String result;
    private String message;
    private String data;

    public ResultBuilder(String result, String message, String data){
        this.result = result;
        this.message = message;
        this.data = data;
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
