package com.example.doan.quanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.activity.MainActivity;
import com.example.doan.activity.NguoiDungActivity;
import com.example.doan.inteface.ApiDienThoai;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    PieChart pieChart;
    BarChart barChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDienThoai apiDienThoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apiDienThoai= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        pieChart=findViewById(R.id.piechart);
        barChart=findViewById(R.id.barchar);
        toolbar=findViewById(R.id.ToolbarThongKe);
        getDataPie();
        initBar();
        setupBar();
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

    private void setupBar() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setAxisMinimum(1f); // số nguyên được hiển thị đúng giữa các cột
        xAxis.setAxisMaximum(12f); // số nguyên được hiển thị đúng giữa các cột
        xAxis.setLabelCount(12); // Chỉ hiển thị 12 nhãn trên trục x
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        yAxisRight.setGranularity(1); // số nguyên trên trục y
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setGranularity(1); //số nguyên trên trục y
    }

    private void getDataPie() {
        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        List<PieEntry> list=new ArrayList<>();
        compositeDisposable.add(apiDienThoai.getSanPhamBanChay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel -> {
                            for (int i=0;i<thongkeModel.getResult().size();i++){
                                String tensanpham=thongkeModel.getResult().get(i).getTensanpham();
                                int tong=thongkeModel.getResult().get(i).getTong();
                                list.add(new PieEntry(tong, tensanpham));
                            }
                            PieDataSet pieDataSet = new PieDataSet(list, "Thống Kê Các Sản Phẩm Đã Bán");
                            PieData pieData = new PieData(pieDataSet);
                            pieData.setValueTextSize(12f);
                            pieData.setValueFormatter(new PercentFormatter(pieChart));
                            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            pieChart.setData(pieData);
                            pieChart.animateXY(2000,2000);
                            pieChart.setUsePercentValues(true);
                            pieChart.getDescription().setEnabled(false);
                            pieChart.invalidate();
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    private void getDataBar() {
        pieChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        List<BarEntry> barEntryList=new ArrayList<>();
        compositeDisposable.add(apiDienThoai.getDoanhThuThang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel -> {
                            if (thongkeModel.isSuccess()){
                                for (int i=0;i<thongkeModel.getResult().size();i++){
                                    String doanhthu=thongkeModel.getResult().get(i).getTongtienthang();
                                    String thang=thongkeModel.getResult().get(i).getThang();
                                    barEntryList.add(new BarEntry(Integer.parseInt(thang),Float.parseFloat(doanhthu)));
                                }
                                BarDataSet barDataSet = new BarDataSet(barEntryList,"Thống Kê Doanh Thu Tháng");
                                barDataSet.setValueTextSize(12f);
                                barDataSet.setValueTextColor(Color.RED);
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                BarData data=new BarData(barDataSet);
                                barChart.setData(data);
                                barChart.animateXY(2000,2000);
                                barChart.invalidate();
                            }
                        }
                ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menuthongke,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.thongkesanphamban) {
             getDataPie();
            return true;
        } else if (id == R.id.thongkedoanhthuthang) {
             getDataBar();
            return true;
        }
        return false;
    }
}