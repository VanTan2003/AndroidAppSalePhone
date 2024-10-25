package com.example.doan.Eventbus;

import com.example.doan.model.taikhoan;

public class SuaXoaTaiKhoanEvent {
    taikhoan taikhoan;

    public SuaXoaTaiKhoanEvent(taikhoan taikhoan) {
        this.taikhoan = taikhoan;
    }

    public taikhoan getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(taikhoan taikhoan) {
        this.taikhoan = taikhoan;
    }
}
