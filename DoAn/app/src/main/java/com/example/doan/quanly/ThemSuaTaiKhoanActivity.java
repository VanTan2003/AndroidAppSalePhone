package com.example.doan.quanly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.sanpham;
import com.example.doan.model.taikhoan;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemSuaTaiKhoanActivity extends AppCompatActivity {
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    EditText edthoten, edtemail, edtsodienthoai, edtmatkhau;
    boolean flag = false;
    int quyen = 0;
    Spinner spinner;
    taikhoan taikhoan;
    AppCompatButton btnthemsuataikhoan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_tai_khoan);
        apiDienThoai = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        toolbar = findViewById(R.id.ToolbarThemSuaTaiKhoan);
        btnthemsuataikhoan = findViewById(R.id.btnthemsuataikhoan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spinner = findViewById(R.id.spnquyen);
        initData();

        edtemail = findViewById(R.id.edt_email);
        edthoten = findViewById(R.id.edt_hoten);
        edtmatkhau = findViewById(R.id.edt_matkhau);
        edtsodienthoai = findViewById(R.id.edt_sodienthoai);
        Intent intent = getIntent();
        taikhoan = (taikhoan) intent.getSerializableExtra("Sửa tài khoản");
        if (taikhoan == null) {
            flag = false;
        } else {
            flag = true;
            toolbar.setTitle("Sửa Tài Khoản");
            btnthemsuataikhoan.setText("Sửa Tài Khoản");
            edtsodienthoai.setText(taikhoan.getSodienthoai());
            edtmatkhau.setText(taikhoan.getMatkhau());
            edthoten.setText(taikhoan.getHoten());
            edtemail.setText(taikhoan.getEmail());
            spinner.setSelection(taikhoan.getQuyen());
        }
    }

    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn quyền");
        stringList.add("Admin");
        stringList.add("User");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quyen = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnthemsuataikhoan.setOnClickListener(view -> {
            if (flag == false) {
                themtaikhoan();
            } else {
                suataikhoan();

            }
        });
    }

    private void suataikhoan() {
        String str_ten = edthoten.getText().toString().trim();
        String str_email = edtemail.getText().toString().trim();
        String str_matkhau = edtmatkhau.getText().toString().trim();
        String str_sodienthoai = edtsodienthoai.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_matkhau)||TextUtils.isEmpty(str_sodienthoai)|| quyen == 0) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            Toast.makeText(getApplicationContext(), "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
        }
        else if (!str_sodienthoai.matches("0[0-9]{9}")) {
            Toast.makeText(getApplicationContext(), "Số điện thoại không đúng định dạng!", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiDienThoai.suaTaiKhoan(str_ten, str_email, str_matkhau, str_sodienthoai, quyen, taikhoan.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            taikhoanModel -> {
                                if (taikhoanModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), QuanLyTaiKhoanActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sưa thất bai", Toast.LENGTH_LONG).show();
                                }
                            }, throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }));
        }
    }

    private void themtaikhoan() {
        String str_ten = edthoten.getText().toString().trim();
        String str_email = edtemail.getText().toString().trim();
        String str_matkhau = edtmatkhau.getText().toString().trim();
        String str_sodienthoai = edtsodienthoai.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_matkhau)||TextUtils.isEmpty(str_sodienthoai)|| quyen == 0) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            Toast.makeText(getApplicationContext(), "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
        }
        else if (!str_sodienthoai.matches("0[0-9]{9}")) {
            Toast.makeText(getApplicationContext(), "Số điện thoại không đúng định dạng!", Toast.LENGTH_SHORT).show();
        }else {
            compositeDisposable.add(apiDienThoai.dangKy(str_ten,str_email, str_matkhau, str_sodienthoai, quyen)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            taikhoanModel -> {
                                if (taikhoanModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(getApplicationContext(), QuanLyTaiKhoanActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Thêm thất bai",Toast.LENGTH_LONG).show();
                                }
                            }, throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                            }));

        }
    }
}