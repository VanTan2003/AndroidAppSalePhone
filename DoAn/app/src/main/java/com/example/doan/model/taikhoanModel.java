package com.example.doan.model;

import java.util.List;

public class taikhoanModel {
    boolean success;
    String message;
    List<taikhoan> result;
    int quyen;

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
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

    public List<taikhoan> getResult() {
        return result;
    }

    public void setResult(List<taikhoan> result) {
        this.result = result;
    }
}
