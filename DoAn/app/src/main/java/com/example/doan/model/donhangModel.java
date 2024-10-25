package com.example.doan.model;

import java.util.List;

public class donhangModel {
    boolean success;
    String message;
    List<donhang> result;

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

    public List<donhang> getResult() {
        return result;
    }

    public void setResult(List<donhang> result) {
        this.result = result;
    }
}
