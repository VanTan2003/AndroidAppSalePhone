package com.example.doan.model;

public class loaisanpham {
    int id;
    String tenloai;
    String hinhanh;

    public loaisanpham(String tenloai, String hinhanh) {
        this.tenloai = tenloai;
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}

