package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.timkiemAdapter;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.sanpham;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimKiemActivity extends AppCompatActivity {
    timkiemAdapter timkiemAdapter;
    List<sanpham> sanphamList;
    ApiDienThoai apiDienThoai;
    EditText edtNhap;
    Toolbar toolbar;
    NotificationBadge badge;
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Spinner spinnerTimKiem;
    Spinner spinnerKhoangGia;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        edtNhap=findViewById(R.id.txttimkiemnhap);
        recyclerView=findViewById(R.id.RecyclerViewTimKiem);
        spinnerTimKiem = findViewById(R.id.spinnerTimKiem);
        spinnerKhoangGia = findViewById(R.id.spinnerKhoanggia);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        toolbar=findViewById(R.id.ToolbarTimKiem);
        sanphamList=new ArrayList<>();
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
        initBar();
        List<String> stringList = new ArrayList<>();
        stringList.add("Tìm kiếm theo tên");
        stringList.add("Tìm kiếm theo khoảng giá");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinnerTimKiem.setAdapter(adapter);
        spinnerTimKiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    sanphamList.clear();
                    edtNhap.setVisibility(View.VISIBLE);
                    spinnerKhoangGia.setVisibility(View.GONE);
                } else {
                    sanphamList.clear();
                    edtNhap.setVisibility(View.GONE);
                    spinnerKhoangGia.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        List<String> stringKhoangGia = new ArrayList<>();
        stringKhoangGia.add("Vui Lòng Chọn Khoảng Giá");
        stringKhoangGia.add("2tr đến 5tr");
        stringKhoangGia.add("5tr đến 10tr");
        stringKhoangGia.add("10tr đến 20tr");
        stringKhoangGia.add("20tr đến 50tr");
        ArrayAdapter<String> adapterkhoanggia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringKhoangGia);
        spinnerKhoangGia.setAdapter(adapterkhoanggia);
        spinnerKhoangGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sanphamList.clear();
                    timkiemAdapter = new timkiemAdapter(getApplicationContext(), sanphamList);
                    recyclerView.setAdapter(timkiemAdapter);
                }
                String minPrice = "";
                String maxPrice = "";
                switch (position) {
                    case 1:
                        minPrice = "2000000";
                        maxPrice = "5000000";
                        break;
                    case 2: // 5tr đến 10tr
                        minPrice = "5000000";
                        maxPrice = "10000000";
                        break;
                    case 3:
                        minPrice = "10000000";
                        maxPrice = "20000000";
                        break;
                    case 4:
                        minPrice = "20000000";
                        maxPrice = "50000000";
                        break;
                    default:
                        break;
                }
                timKiemTheoGia(minPrice, maxPrice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        edtNhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    sanphamList.clear();
                    timkiemAdapter = new timkiemAdapter(getApplicationContext(), sanphamList);
                    recyclerView.setAdapter(timkiemAdapter);
                } else {
                    getDataSearch(charSequence.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void timKiemTheoGia(String minPrice, String maxPrice) {
        compositeDisposable.add(apiDienThoai.timKiemTheoGia(minPrice, maxPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel -> {
                            if (sanphamModel.isSuccess()) {
                                sanphamList = sanphamModel.getResult();
                                timkiemAdapter = new timkiemAdapter(getApplicationContext(), sanphamList);
                                recyclerView.setAdapter(timkiemAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }

    private void initBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDataSearch(String s) {
        sanphamList.clear();
        compositeDisposable.add(apiDienThoai.timKiem(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel -> {
                            if (sanphamModel.isSuccess()) {
                                sanphamList = sanphamModel.getResult();
                                timkiemAdapter = new timkiemAdapter(getApplicationContext(), sanphamList);
                                recyclerView.setAdapter(timkiemAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
