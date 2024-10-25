package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.sanphamlienquanAdapter;
import com.example.doan.adapter.sanphammoiAdapter;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.giohang;
import com.example.doan.model.sanpham;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewSanPhamLienQuan;
    List<sanpham> mangsanphamlienquan;
    sanpham sanpham;
    TextView txttensanpham,txtgiasanpham,txtmota;
    Spinner spinner;
    ImageView imgagesanpham,imageseach;
    sanphamlienquanAdapter sanphamlienquanAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
     AppCompatButton btnthemgiohang;
    ApiDienThoai apiDienThoai;
    NotificationBadge badge;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        recyclerViewSanPhamLienQuan=findViewById(R.id.recyclerview_sanphamlienquan);
        mangsanphamlienquan=new ArrayList<>();
        badge=findViewById(R.id.badgesoluong);
        frameLayout=findViewById(R.id.frameLayout_giohang);
        imageseach=findViewById(R.id.imageseach);
        imageseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
        txtgiasanpham=findViewById(R.id.txt_giasanphamchitiet);
        txttensanpham=findViewById(R.id.txt_tensanphamchitiet);
        txtmota=findViewById(R.id.txt_motachitiet);
        btnthemgiohang=findViewById(R.id.btnthemvaogiohang);
        btnthemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themgiohang();
            }
        });
        spinner=findViewById(R.id.spinnersoluong);
        imgagesanpham=findViewById(R.id.img_sanphamchitiet);
        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSanPhamLienQuan.setLayoutManager(horizontalLayoutManager);
        recyclerViewSanPhamLienQuan.setHasFixedSize(true);
        toolbar=findViewById(R.id.ToolbarChiTietSanPham);
        getActionBarc();
        getSanPhamLienQuan();
        sanpham = (sanpham) getIntent().getSerializableExtra("chitiet");
        txttensanpham.setText(sanpham.getTensanpham());
        txtmota.setText(sanpham.getMota());
        if (sanpham.getHinhanh().contains("http")){
            Glide.with(getApplicationContext()).load(sanpham.getHinhanh()).into(imgagesanpham);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).into(imgagesanpham);

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiasanpham.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanpham.getGiasp()))+"Đ");
        List<Integer> so=new ArrayList<>();
        for (int i=1;i<sanpham.getSoluongkho()+1;i++){
            so.add(i);
        }
        ArrayAdapter<Integer> adapterspnier = new ArrayAdapter<>(this, io.paperdb.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspnier);
        if (Utils.manggiohang!=null){
            badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
    }

    private void themgiohang() {
        if (Utils.manggiohang.size()>0){
            boolean flag  = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0 ; i< Utils.manggiohang.size(); i++){
                if (Utils.manggiohang.get(i).getIdsp() == sanpham.getId()){
                    int soluongGioHang = Utils.manggiohang.get(i).getSoluong();
                    if (soluongGioHang + soluong <= sanpham.getSoluongkho()) {
                        Utils.manggiohang.get(i).setSoluong(soluong + soluongGioHang);
                    } else {
                        Toast.makeText(this, "Số lượng vượt quá số lượng trong kho", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    flag = true;
                    break;
                }
            }
            if (flag == false){
                long gia = Long.parseLong(sanpham.getGiasp()) * soluong;
                giohang gioHang = new giohang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanpham.getId());
                gioHang.setTensanpham(sanpham.getTensanpham());
                gioHang.setHinhanh(sanpham.getHinhanh());
                gioHang.setSoluongkho(sanpham.getSoluongkho());
                Utils.manggiohang.add(gioHang);
            }

        }else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanpham.getGiasp()) * soluong;
            giohang gioHang = new giohang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanpham.getId());
            gioHang.setTensanpham(sanpham.getTensanpham());
            gioHang.setHinhanh(sanpham.getHinhanh());
            gioHang.setSoluongkho(sanpham.getSoluongkho());
            Utils.manggiohang.add(gioHang);
        }
        badge.setText(String.valueOf(Utils.manggiohang.size()));
    }

    private void getSanPhamLienQuan() {
        compositeDisposable.add(apiDienThoai.getSanPhamBanChay1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel -> {
                            if (sanphamModel.isSuccess()){
                                mangsanphamlienquan = sanphamModel.getResult();
                                sanphamlienquanAdapter= new sanphamlienquanAdapter(getApplicationContext(),mangsanphamlienquan);
                                recyclerViewSanPhamLienQuan.setAdapter(sanphamlienquanAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null) {
            badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
    }

    private void getActionBarc() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}