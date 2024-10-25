package com.example.doan.model;

import java.util.List;

public class slideModel {
    boolean success;
    String message;
    List<slide> result;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<slide> getResult() {
        return result;
    }

    public void setResult(List<slide> result) {
        this.result = result;
    }
}
