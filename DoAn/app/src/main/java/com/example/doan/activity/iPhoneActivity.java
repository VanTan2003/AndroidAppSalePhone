package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.loaisanphamAdapter;
import com.example.doan.adapter.sanphamAdapter;
import com.example.doan.adapter.sanphammoiAdapter;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.sanpham;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class iPhoneActivity extends AppCompatActivity {
    RecyclerView recyclerViewSanPham;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    List<sanpham> mangspmoi;
    int loai;
    Toolbar toolbar;
    sanphamAdapter sanphamAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iphone);
        mangspmoi=new ArrayList<>();
        toolbar=findViewById(R.id.ToolbariPhone);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        loai=getIntent().getIntExtra("idloai",1);
        recyclerViewSanPham=findViewById(R.id.RecyclerViewiPhone);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,3);
        recyclerViewSanPham.setLayoutManager(layoutManager);
        recyclerViewSanPham.setHasFixedSize(true);
        ImageView imageseach=findViewById(R.id.imageseach);
        imageseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
        getSanPham();
        getActionBarr();
        badge=findViewById(R.id.badgesoluong);
        frameLayout=findViewById(R.id.frameLayout_giohang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
        if (Utils.manggiohang!=null){
            badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
    }

    private void getActionBarr() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getSanPham() {
        compositeDisposable.add(apiDienThoai.getSanPham(loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel -> {
                            if (sanphamModel.isSuccess()) {
                                mangspmoi = sanphamModel.getResult();
                                sanphamAdapter = new sanphamAdapter(getApplicationContext(),mangspmoi);
                                recyclerViewSanPham.setAdapter(sanphamAdapter);
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
}