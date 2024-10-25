package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.inteface.ApiDienThoai;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    AppCompatButton btnthanhtoan;
    TextView txttongtien,txtemail,txtsodienthoai,txthoten;
    EditText edtdiachi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    Toolbar toolbar;
    int totalItem;
    long tongtien;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        edtdiachi=findViewById(R.id.edt_diachigiaohang);
        txtemail=findViewById(R.id.txt_emailthanhtoan);
        toolbar=findViewById(R.id.ToolbarThanhToan);
        txthoten=findViewById(R.id.txt_tenthanhtoan);
        txtsodienthoai=findViewById(R.id.txt_sdtthanhtoan);
        txttongtien=findViewById(R.id.txttongtienthanhtoan);
        btnthanhtoan=findViewById(R.id.btndathang);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tongtien=getIntent().getLongExtra("tongtien",0);
        txttongtien.setText(decimalFormat.format(tongtien)+"VND");
        txtemail.setText(Utils.user_current.getEmail());
        txthoten.setText(Utils.user_current.getHoten());
        txtsodienthoai.setText(Utils.user_current.getSodienthoai());
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diachi=edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(diachi)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập địa chir", Toast.LENGTH_SHORT).show();
                }
                else {
                    String email = Utils.user_current.getEmail();
                    String sodienthoai = Utils.user_current.getSodienthoai();
                    int id = Utils.user_current.getId();
                    Log.d("Test",new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiDienThoai.datHang(diachi, email, sodienthoai, id, totalItem, String.valueOf(tongtien),0, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    donhangModel -> {
                                        Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                        Utils.mangmuahang.clear();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    error -> {
                                        Log.e("ThanhToanActivity", "Error placing order:", error);
                                    }
                            ));
                }
            }
        });
        initBar();
        Soluongsanpham();
    }

    private void Soluongsanpham() {
        totalItem = 0;
        for (int i = 0 ; i<Utils.mangmuahang.size();i++){
            totalItem =  totalItem+Utils.mangmuahang.get(i).getSoluong();
        }
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
}