package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.quanly.QuanLyTaiKhoanActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongTinNguoiDungActivity extends AppCompatActivity {
    EditText edthoten,edtemail,edtsodienthoai,edtmatkhau;
    Toolbar toolbar;
    AppCompatButton btnLuu;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ApiDienThoai apiDienThoai;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);
        edtemail=findViewById(R.id.edt_emailtt);
        edthoten=findViewById(R.id.edt_hotentt);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        edtsodienthoai=findViewById(R.id.edt_sodienthoaitt);
        edtmatkhau=findViewById(R.id.edt_matkhautt);
        edtemail.setText(Utils.user_current.getEmail());
        edthoten.setText(Utils.user_current.getHoten());
        edtsodienthoai.setText(Utils.user_current.getSodienthoai());
        edtmatkhau.setText(Utils.user_current.getMatkhau());
        toolbar=findViewById(R.id.ToolbarThongTinCaNhan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnLuu=findViewById(R.id.btnluu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thaydoithongtin();
            }
        });
    }

    private void thaydoithongtin() {
        String str_ten = edthoten.getText().toString().trim();
        String str_email = edtemail.getText().toString().trim();
        String str_matkhau = edtmatkhau.getText().toString().trim();
        String str_sodienthoai = edtsodienthoai.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_matkhau)||TextUtils.isEmpty(str_sodienthoai)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            Toast.makeText(getApplicationContext(), "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
        }
        else if (!str_sodienthoai.matches("0[0-9]{9}")) {
            Toast.makeText(getApplicationContext(), "Số điện thoại không đúng định dạng!", Toast.LENGTH_SHORT).show();
        }else {
            compositeDisposable.add(apiDienThoai.suaTaiKhoan(str_ten, str_email, str_matkhau, str_sodienthoai, 2, Utils.user_current.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            taikhoanModel -> {
                                if (taikhoanModel.isSuccess()) {
                                    Utils.user_current.setHoten(str_ten);
                                    Utils.user_current.setEmail(str_email);
                                    Utils.user_current.setSodienthoai(str_sodienthoai);
                                    Utils.user_current.setMatkhau(str_matkhau);
                                    Toast.makeText(getApplicationContext(), "Thay đổi thành công", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), NguoiDungActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Thay đổi thất bai", Toast.LENGTH_LONG).show();
                                }
                            }, throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }));
        }
    }
}