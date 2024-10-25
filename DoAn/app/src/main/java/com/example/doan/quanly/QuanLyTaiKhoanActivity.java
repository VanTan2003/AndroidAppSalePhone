package com.example.doan.quanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.doan.Eventbus.SuaXoaSanPhamEvent;
import com.example.doan.Eventbus.SuaXoaTaiKhoanEvent;
import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.sanphamquanlyAdapter;
import com.example.doan.adapter.taikhoanAdapter;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.sanpham;
import com.example.doan.model.taikhoan;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLyTaiKhoanActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    taikhoan taikhoan;
    List<taikhoan> taikhoanList;
    taikhoanAdapter taikhoanAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        recyclerView=findViewById(R.id.RecyclerViewQuanLyTaiKhoan);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        taikhoanList=new ArrayList<>();
        toolbar=findViewById(R.id.ToolbarQuanLyTaiKhoan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        linearLayout=findViewById(R.id.linerlayout_them);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ThemSuaTaiKhoanActivity.class);
                startActivity(intent);
            }
        });
        getData();
    }

    private void getData() {
        compositeDisposable.add(apiDienThoai.getTaiKhoan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        taikhoanModel -> {
                            if (taikhoanModel.isSuccess()){
                                taikhoanAdapter=new taikhoanAdapter(getApplicationContext(),taikhoanModel.getResult());
                                recyclerView.setAdapter(taikhoanAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                        }
                ));
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Sửa tài khoản")){
            capnhattaikhoan();
        }else if (item.getTitle().equals("Xóa tài khoản")){
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa tài khoản này?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            xoataikhoan();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    private void xoataikhoan() {
        compositeDisposable.add(apiDienThoai.xoaTaiKhoan(taikhoan.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        taikhoanModel -> {
                            if (taikhoanModel.isSuccess()){
                                Toast.makeText(getApplicationContext(),taikhoanModel.getMessage(),Toast.LENGTH_SHORT).show();
                                getData();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),taikhoanModel.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        },throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void capnhattaikhoan() {
        Intent intent = new Intent(getApplicationContext(), ThemSuaTaiKhoanActivity.class);
        intent.putExtra("Sửa tài khoản",taikhoan);
        startActivity(intent);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenSuaXoa(SuaXoaTaiKhoanEvent event){
        if (event != null){
            taikhoan = event.getTaikhoan();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}