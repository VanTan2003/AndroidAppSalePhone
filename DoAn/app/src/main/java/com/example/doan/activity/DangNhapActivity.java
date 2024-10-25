package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.taikhoan;
import com.example.doan.quanly.QuanLyActivity;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    TextView txtdangky;
    EditText txtemail,txtmatkhau;
    AppCompatButton btndangnhap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        txtemail=findViewById(R.id.emaildangnhap);
        txtmatkhau=findViewById(R.id.matkhaudannhap);
        btndangnhap=findViewById(R.id.btndangnhap);
        txtdangky=findViewById(R.id.txtdangky);
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangnhap();
            }
        });
        Paper.init(this);
        if (Paper.book().read("email")!=null&&Paper.book().read("matkhau")!=null){
            txtemail.setText(Paper.book().read("email"));
            txtmatkhau.setText(Paper.book().read("matkhau"));
        }
    }

    private void dangnhap() {
        String email=txtemail.getText().toString().trim();
        String matkhau=txtmatkhau.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập email!",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(matkhau)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu!",Toast.LENGTH_SHORT).show();
        }
        else {
            Paper.book().write("email",email);
            Paper.book().write("matkhau",matkhau);
            compositeDisposable.add(apiDienThoai.dangNhap(email,matkhau)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            taikhoanModel -> {
                                if (taikhoanModel.isSuccess()){
                                    int quyen = taikhoanModel.getQuyen();
                                    if (quyen == 2) {
                                        Utils.user_current=taikhoanModel.getResult().get(0);
                                        Toast.makeText(getApplicationContext(),"Chào mừng bạn đăng nhập",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else if (quyen == 1) {
                                        Utils.user_current=taikhoanModel.getResult().get(0);
                                        Toast.makeText(getApplicationContext(),"Chào mừng admin đăng nhập",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DangNhapActivity.this, QuanLyActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),taikhoanModel.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail()!=null && Utils.user_current.getMatkhau()!=null){
            txtemail.setText(Utils.user_current.getEmail());
            txtmatkhau.setText(Utils.user_current.getMatkhau());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}