package com.example.doan.Eventbus;

import com.example.doan.model.sanpham;

public class SuaXoaSanPhamEvent {
    sanpham sanpham;

    public SuaXoaSanPhamEvent(sanpham sanpham) {
        this.sanpham = sanpham;
    }

    public sanpham getSanpham() {
        return sanpham;
    }

    public void setSanpham(sanpham sanpham) {
        this.sanpham = sanpham;
    }
}
