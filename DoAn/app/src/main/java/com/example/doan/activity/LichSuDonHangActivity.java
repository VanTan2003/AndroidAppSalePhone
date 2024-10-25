package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.donhangAdapter;
import com.example.doan.inteface.ApiDienThoai;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LichSuDonHangActivity extends AppCompatActivity {

    ApiDienThoai apiDienThoai;
    RecyclerView recyclerViewlichsudon;
    Toolbar toolbar;
    donhangAdapter donhangAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_don_hang);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        recyclerViewlichsudon=findViewById(R.id.RecyclerViewLichSuDon);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerViewlichsudon.setLayoutManager(layoutManager);
        recyclerViewlichsudon.setHasFixedSize(true);
        toolbar=findViewById(R.id.ToolbarLichDonHang);
        getDonHang();
        initBar();
    }

    private void initBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), NguoiDungActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getDonHang() {
        int id=Utils.user_current.getId();
        compositeDisposable.add(apiDienThoai.xemDonHang(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donhangModel -> {
                            donhangAdapter = new donhangAdapter(getApplicationContext(),donhangModel.getResult());
                            recyclerViewlichsudon.setAdapter(donhangAdapter);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }));
    }
}