package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKyActivity extends AppCompatActivity {
    EditText txthoten,txtemail,txtsodienthoai,txtmatkhau,txtnhaplaimatkhau;
    AppCompatButton btndangky;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    TextView txtdangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        txthoten=findViewById(R.id.hotendangky);
        txtemail=findViewById(R.id.emaildangky);
        txtdangnhap=findViewById(R.id.txtdangnhap);
        txtdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
        txtsodienthoai=findViewById(R.id.sodienthoaidangky);
        txtmatkhau=findViewById(R.id.matkhaudangky);
        txtnhaplaimatkhau=findViewById(R.id.nhaplaimatkhau);
        btndangky=findViewById(R.id.btndangky);
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangky();
            }
        });
    }

    private void dangky() {
        String hoten=txthoten.getText().toString().trim();
        String email=txtemail.getText().toString().trim();
        String sodienthoai=txtsodienthoai.getText().toString().trim();
        String matkhau=txtmatkhau.getText().toString().trim();
        String nhaplaimatkhau=txtnhaplaimatkhau.getText().toString().trim();
        if (TextUtils.isEmpty(hoten)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập họ tên!",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập email!",Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(sodienthoai)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu!",Toast.LENGTH_SHORT).show(); }
        else if (TextUtils.isEmpty(matkhau)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập lại mật khẩu!",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(nhaplaimatkhau)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập số điện thoại!",Toast.LENGTH_SHORT).show();
        } else if (!sodienthoai.matches("0[0-9]{9}")) {
            Toast.makeText(getApplicationContext(), "Số điện thoại không đúng định dạng!", Toast.LENGTH_SHORT).show();
        }else {
          if (matkhau.equals(nhaplaimatkhau)){
              compositeDisposable.add(apiDienThoai.dangKy(hoten,email,matkhau,sodienthoai,2)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(
                              taikhoanModel -> {
                                   if (taikhoanModel.isSuccess()){
                                       Utils.user_current.setEmail(email);
                                       Utils.user_current.setMatkhau(matkhau);
                                       Toast.makeText(getApplicationContext(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                       Intent intent=new Intent(getApplicationContext(), DangNhapActivity.class);
                                       startActivity(intent);
                                   }
                              }
                      ));
          }
          else {
              Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại chưa khớp!", Toast.LENGTH_SHORT).show();
          }
        }
    }
}