package com.example.doan.quanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.doan.Eventbus.SuaXoaSanPhamEvent;
import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.sanphamquanlyAdapter;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.sanpham;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLyDienThoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    sanpham sanpham;
    List<sanpham> sanphamList;
    sanphamquanlyAdapter sanphamquanlyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_dien_thoai);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        recyclerView=findViewById(R.id.RecyclerViewQuanLySanPham);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        sanphamList=new ArrayList<>();
        toolbar=findViewById(R.id.ToolbarQuanLyDienThoai);
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
                Intent intent=new Intent(getApplicationContext(), ThemSuaDienThoaiActivity.class);
                startActivity(intent);
            }
        });
        getData();
    }

    private void getData() {
        compositeDisposable.add(apiDienThoai.getSanPhamMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel -> {
                            if (sanphamModel.isSuccess()){
                                sanphamList=sanphamModel.getResult();
                                sanphamquanlyAdapter=new sanphamquanlyAdapter(getApplicationContext(), sanphamList);
                                recyclerView.setAdapter(sanphamquanlyAdapter);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),sanphamModel.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Sửa sản phẩm")){
            capnhatsanpham();
        }else if (item.getTitle().equals("Xóa sản phẩm")){
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            xoasanpham();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    private void xoasanpham() {
        compositeDisposable.add(apiDienThoai.xoaSanPham(sanpham.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel -> {
                           if (sanphamModel.isSuccess()){
                               Toast.makeText(getApplicationContext(),sanphamModel.getMessage(),Toast.LENGTH_SHORT).show();
                               getData();
                           }
                           else {
                               Toast.makeText(getApplicationContext(),sanphamModel.getMessage(),Toast.LENGTH_SHORT).show();
                           }
                        },throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void capnhatsanpham() {
        Intent intent = new Intent(getApplicationContext(), ThemSuaDienThoaiActivity.class);
        intent.putExtra("Sửa sản phẩm",sanpham);
        startActivity(intent);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenSuaXoa(SuaXoaSanPhamEvent event){
        if (event != null){
            sanpham = event.getSanpham();
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