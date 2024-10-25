package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.Eventbus.TinhTongEvent;
import com.example.doan.R;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.giohangAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    long tongtiensp;
    TextView txttongtien,txtgiohangtrong;
    giohangAdapter giohangAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    AppCompatButton btnmuahang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        recyclerView=findViewById(R.id.RecyclerViewGioHang);
        toolbar=findViewById(R.id.ToolbarGioHang);
        btnmuahang=findViewById(R.id.btnmuahang);
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                if (tongtiensp == i){
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn ít nhất 1 sản phẩm", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(getApplicationContext(), ThanhToanActivity.class);
                    intent.putExtra("tongtien",tongtiensp);
                    Utils.manggiohang.clear();
                    startActivity(intent);
                    finish();
                }
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        txttongtien=findViewById(R.id.txttongtiengiohang);
        txtgiohangtrong=findViewById(R.id.txtgiohangtrong);
        if (Utils.manggiohang.size()==0){
            txtgiohangtrong.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else{
            txtgiohangtrong.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            giohangAdapter = new giohangAdapter(getApplicationContext(),Utils.manggiohang);
            recyclerView.setAdapter(giohangAdapter);
        }
        initBar();
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

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhtien(TinhTongEvent event){
        if (event != null){
            tinhtongtien();
        }
    }

    private void tinhtongtien() {
        tongtiensp = 0;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        for (int i = 0; i< Utils.mangmuahang.size(); i++){
            tongtiensp = tongtiensp + (Utils.mangmuahang.get(i).getGiasp()*Utils.mangmuahang.get(i).getSoluong());
        }
        txttongtien.setText(decimalFormat.format(tongtiensp));
    }
}