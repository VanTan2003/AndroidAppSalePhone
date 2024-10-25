package com.example.doan.model;

import java.io.Serializable;

public class sanpham implements Serializable {
    String tensanpham;
    String hinhanh;
    String giasp;
    String mota;
    int soluongkho;
    int loai;
    int id;

    public sanpham(String tensanpham, String hinhanh, String giasp, String mota, int soluongkho, int loai, int id) {
        this.tensanpham = tensanpham;
        this.hinhanh = hinhanh;
        this.giasp = giasp;
        this.mota = mota;
        this.soluongkho = soluongkho;
        this.loai = loai;
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getSoluongkho() {
        return soluongkho;
    }

    public void setSoluongkho(int soluongkho) {
        this.soluongkho = soluongkho;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
