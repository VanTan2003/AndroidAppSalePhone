package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doan.R;
import com.example.doan.Utils.Utils;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NguoiDungActivity extends AppCompatActivity {
    LinearLayout lichsumuahang,dangxuat,btnthongtin;
    TextView txthoten,txtemail,txtsodienthoai;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        lichsumuahang=findViewById(R.id.lichsumuahang);
        btnthongtin=findViewById(R.id.btnthongtin);
        txtemail=findViewById(R.id.txtemailuser);
        txtsodienthoai=findViewById(R.id.txtsodienthoaiuser);
        txthoten=findViewById(R.id.txthotenuser);
        txtemail.setText(Utils.user_current.getEmail());
        txthoten.setText(Utils.user_current.getHoten());
        txtsodienthoai.setText(Utils.user_current.getSodienthoai());
        toolbar=findViewById(R.id.ToolbarCaNhan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        dangxuat=findViewById(R.id.dangxuat);
        dangxuat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
               builder.setTitle("Thông Báo");
               builder.setMessage("Bạn chắc chắn muốn đăng xuất");
               builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                   Intent intent=new Intent(getApplicationContext(),ManHinhChoActivity.class);
                   startActivity(intent);
                   finish();
               });
               builder.setNegativeButton("Hủy bỏ", (dialogInterface, i) -> dialogInterface.dismiss());
               builder.show();
           }
       });
        lichsumuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), LichSuDonHangActivity.class);
                startActivity(intent);
            }
        });
        btnthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ThongTinNguoiDungActivity.class);
                startActivity(intent);
            }
        });
    }
}