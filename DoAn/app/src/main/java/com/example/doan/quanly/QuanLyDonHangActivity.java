package com.example.doan.quanly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doan.Eventbus.DonHangEventBus;
import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.donhangAdapter;
import com.example.doan.adapter.donhangadminAdapter;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.donhang;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLyDonHangActivity extends AppCompatActivity {
    ApiDienThoai apiDienThoai;
    RecyclerView recyclerViewlichsudon;
    Toolbar toolbar;
    donhangadminAdapter donhangAdapter;
    donhang donhang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int tinhtrang;
    AlertDialog dialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        recyclerViewlichsudon=findViewById(R.id.RecyclerViewQuanLyDonHang);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerViewlichsudon.setLayoutManager(layoutManager);
        recyclerViewlichsudon.setHasFixedSize(true);
        toolbar=findViewById(R.id.ToolbarQuanLyDonHang);
        initBar();
        getDonHang();
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

    private void getDonHang() {
        compositeDisposable.add(apiDienThoai.getDonHang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donhangModel -> {
                            if (donhangModel.isSuccess()){
                                donhangAdapter=new donhangadminAdapter(getApplicationContext(),donhangModel.getResult());
                                recyclerViewlichsudon.setAdapter(donhangAdapter);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),donhangModel.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    private void showCustumDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_donhang, null);
        Spinner spinner = view.findViewById(R.id.spinner_dialog);
        AppCompatButton btn_xacnhan = view.findViewById(R.id.chapnhan);
        List<String> list = new ArrayList<>();
        list.add("Đơn hàng đang được xử lí");
        list.add("Xác nhận đơn hàng");
        list.add("Đơn hàng đã giao đến đơn vị vấn chuyển");
        list.add("Đang trên đường giao đến bạn");
        list.add("Giao hàng thành công");
        list.add("Hủy đơn hàng");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        // vị trí cần chọn
        spinner.setSelection(donhang.getTrangthai());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tinhtrang = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (donhang.getTrangthai() == 5 || donhang.getTrangthai() == 4) {
            btn_xacnhan.setEnabled(false);
        } else {
            btn_xacnhan.setEnabled(true);
        }
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capNhatdonhang();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void capNhatdonhang() {
        compositeDisposable.add(apiDienThoai.updateTrangThai(donhang.getId(), tinhtrang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donhangModel -> {
                            if (donhangModel.isSuccess()){
                                Toast.makeText(getApplicationContext(),donhangModel.getMessage(),Toast.LENGTH_SHORT).show();
                                getDonHang();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void evenDonhang(DonHangEventBus event) {
        if (event != null) {
            donhang = event.getDonhang();
            showCustumDialog();
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