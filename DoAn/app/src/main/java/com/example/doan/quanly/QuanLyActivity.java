package com.example.doan.quanly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doan.R;
import com.example.doan.activity.ManHinhChoActivity;

public class QuanLyActivity extends AppCompatActivity {
    AppCompatButton btnquanlydienthoai,btnquanlytaikhoan,btnquanlydonhang,btnthongke,btndangxuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        btnquanlydienthoai=findViewById(R.id.btnquanlydienthoai);
        btnquanlytaikhoan=findViewById(R.id.btnquanlytaikhoan);
        btnquanlydonhang=findViewById(R.id.btnquanlydonhang);
        btnthongke=findViewById(R.id.btnthongke);
        btndangxuat=findViewById(R.id.btndangxuat);
        btnquanlydienthoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),QuanLyDienThoaiActivity.class);
                startActivity(intent);
            }
        });
        btnquanlytaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), QuanLyTaiKhoanActivity.class);
                startActivity(intent);
            }
        });
        btnquanlydonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), QuanLyDonHangActivity.class);
                startActivity(intent);
            }
        });
        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ThongKeActivity.class);
                startActivity(intent);
            }
        });
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ManHinhChoActivity.class);
                startActivity(intent);
            }
        });
    }
}