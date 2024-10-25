package com.example.doan.model;

import java.util.List;

public class sanphamModel {
    boolean success;
    String message;
    List<sanpham> result;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<sanpham> getResult() {
        return result;
    }

    public void setResult(List<sanpham> result) {
        this.result = result;
    }
}
