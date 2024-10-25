package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.loaisanphamAdapter;
import com.example.doan.adapter.sanphammoiAdapter;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.loaisanpham;
import com.example.doan.model.sanpham;
import com.example.doan.model.slideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    loaisanphamAdapter loaisanphamAdapter;
    List<loaisanpham> mangloaisp;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerViewLoaiSanPham,recyclerViewSanPhamMoi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    List<sanpham> mangspmoi;
    sanphammoiAdapter sanphammoiAdapter;
    ImageSlider imageSlider;
    NotificationBadge badge;
    FrameLayout frameLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiDienThoai = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        recyclerViewSanPhamMoi=findViewById(R.id.RecyclerViewSanPhamMoi);
        recyclerViewLoaiSanPham=findViewById(R.id.RecyclerViewLoaiSanPham);
        ImageView imageseach=findViewById(R.id.imageseach);
        imageseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
        bottomNavigationView=findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nguoidung) {
                Intent intent=new Intent(getApplicationContext(),NguoiDungActivity.class);
                startActivity(intent);
                return true;
            }
            // Add more conditions if needed
            return false;
        });
        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewLoaiSanPham.setLayoutManager(horizontalLayoutManager);
        recyclerViewLoaiSanPham.setHasFixedSize(true);
        RecyclerView.LayoutManager linearLayoutManager=new GridLayoutManager(this,3);
        recyclerViewSanPhamMoi.setLayoutManager(linearLayoutManager);
        recyclerViewSanPhamMoi.setHasFixedSize(true);
        mangloaisp = new ArrayList<>();
        mangspmoi = new ArrayList<>();
        imageSlider=findViewById(R.id.image_slider);
        if (Utils.manggiohang==null){
            Utils.manggiohang=new ArrayList<>();
        }
        if (isConnected(this)){
            getSanPhamMoi();
            getLoaiSanPham();
            getBanner();
        }else {
            Toast.makeText(getApplicationContext(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        badge=findViewById(R.id.badgesoluong);
        frameLayout=findViewById(R.id.frameLayout_giohang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (Utils.manggiohang!=null){
            badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
    }

    private void getBanner() {
        List<SlideModel> list= new ArrayList<>();
        compositeDisposable.add(apiDienThoai.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        slideModel -> {
                            if (slideModel.isSuccess()) {
                                for (int i=0;i<slideModel.getResult().size();i++){
                                   list.add(new SlideModel(slideModel.getResult().get(i).getUrl(),null));
                                }
                                imageSlider.setImageList(list, ScaleTypes.FIT);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiDienThoai.getLoaiSanPham()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaisanphamModel -> {
                            if (loaisanphamModel.isSuccess()) {
                                mangloaisp = loaisanphamModel.getResult();
                                loaisanphamAdapter = new loaisanphamAdapter(getApplicationContext(), mangloaisp);
                                recyclerViewLoaiSanPham.setAdapter(loaisanphamAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getSanPhamMoi() {
        compositeDisposable.add(apiDienThoai.getSanPhamMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel -> {
                            if (sanphamModel.isSuccess()){
                                mangspmoi = sanphamModel.getResult();
                                sanphammoiAdapter= new sanphammoiAdapter(getApplicationContext(),mangspmoi);
                                recyclerViewSanPhamMoi.setAdapter(sanphammoiAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }


    boolean isConnected (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi  = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile  = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi != null && wifi.isConnected() || (mobile != null && mobile.isConnected())){
            return true;
        }else {
            return false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null) {
            badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
    }

}