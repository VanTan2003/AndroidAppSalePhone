package com.example.doan.Eventbus;

import com.example.doan.model.donhang;

public class DonHangEventBus {
    donhang donhang;

    public donhang getDonhang() {
        return donhang;
    }

    public DonHangEventBus(donhang donhang) {
        this.donhang = donhang;
    }

    public void setDonhang(donhang donhang) {
        this.donhang = donhang;
    }
}
