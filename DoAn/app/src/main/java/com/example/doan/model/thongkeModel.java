package com.example.doan.model;

import java.util.List;

public class thongkeModel {
    boolean success;
    String message;
    List<thongke> result;

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

    public List<thongke> getResult() {
        return result;
    }

    public void setResult(List<thongke> result) {
        this.result = result;
    }
}
