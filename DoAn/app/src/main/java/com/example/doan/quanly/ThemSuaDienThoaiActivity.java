package com.example.doan.quanly;

import static android.net.Uri.parse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.adapter.sanphamAdapter;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.sanpham;
import com.example.doan.model.sanphamModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSuaDienThoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDienThoai apiDienThoai;
    EditText edtten, edtgia, edtsoluong, edtmota;
    TextView edttenanh;
    int loai = 0;
    Spinner spinner;
    ImageView layout;
    String mediaPath;
    boolean flag = false;
    sanpham sanpham;
    AppCompatButton btnthemsuadienthoai;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_dien_thoai);
        apiDienThoai = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
        toolbar = findViewById(R.id.ToolbarThemSuaDienThoai);
        setSupportActionBar(toolbar);
        btnthemsuadienthoai = findViewById(R.id.btnthemsuadienthoai);
        layout = findViewById(R.id.img_camera);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spinner = findViewById(R.id.spnloai);
        initData();
        edttenanh = findViewById(R.id.edt_tenanh);
        edtten = findViewById(R.id.edt_tensanpham);
        edtgia = findViewById(R.id.edt_giasp);
        edtsoluong = findViewById(R.id.edt_soluong);
        edtmota = findViewById(R.id.edt_mota);
        Intent intent = getIntent();
        sanpham = (sanpham) intent.getSerializableExtra("Sửa sản phẩm");
        if (sanpham == null) {
            flag = false;
        } else {
            flag = true;
            toolbar.setTitle("Sửa Điện Thoại");
            btnthemsuadienthoai.setText("Sửa Sản Phẩm");
            edtten.setText(sanpham.getTensanpham());
            edtgia.setText(sanpham.getGiasp() + "");
            edttenanh.setText(sanpham.getHinhanh());
            edtmota.setText(sanpham.getMota());
            edtsoluong.setText(String.valueOf(sanpham.getSoluongkho()));
            spinner.setSelection(sanpham.getLoai());
        }
    }


    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn loại sản phẩm");
        stringList.add("iPhone");
        stringList.add("Samsung");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnthemsuadienthoai.setOnClickListener(view -> {
            if (flag == false) {
                themsanpham();
            } else {
                suasanpham();

            }
        });

       layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ImagePicker.with(ThemSuaDienThoaiActivity.this)
                       .crop()
                       .compress(1024)
                       .maxResultSize(1080, 1080)
                       .start();
           }
       });
    }
    private void themsanpham() {
        String str_ten = edtten.getText().toString().trim();
        String str_gia = edtgia.getText().toString().trim();
        String str_hinhanh = edttenanh.getText().toString().trim();
        String str_mota = edtmota.getText().toString().trim();
        String str_soluong = edtsoluong.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_hinhanh)||TextUtils.isEmpty(str_soluong)
                || TextUtils.isEmpty(str_mota) || loai == 0) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiDienThoai.themDienThoai(str_ten,str_hinhanh, str_mota, loai, Integer.parseInt(str_soluong), str_gia)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            sanphamModel -> {
                                if (sanphamModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(getApplicationContext(),QuanLyDienThoaiActivity.class);
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
    private void suasanpham() {
        String str_ten = edtten.getText().toString().trim();
        String str_gia = edtgia.getText().toString().trim();
        String str_hinhanh = edttenanh.getText().toString().trim();
        String str_mota = edtmota.getText().toString().trim();
        String str_soluong = edtsoluong.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_hinhanh)||TextUtils.isEmpty(str_soluong)
                || TextUtils.isEmpty(str_mota) || loai == 0) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiDienThoai.suaDienThoai(str_ten,str_hinhanh, str_mota, loai, Integer.parseInt(str_soluong), str_gia,sanpham.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            sanphamModel -> {
                                if (sanphamModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(getApplicationContext(),QuanLyDienThoaiActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Sửa thất bai",Toast.LENGTH_LONG).show();
                                }
                            }, throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                            }));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
    }

    private void uploadMultipleFiles() {
        try {
            File file;
            Uri uri;
            uri = parse(mediaPath);
            file = new File(getPath(uri));
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
            Call<sanphamModel> call = apiDienThoai.uploadFile(fileToUpload1);
            call.enqueue(new Callback<sanphamModel>() {
                @Override
                public void onResponse(Call<sanphamModel> call, Response<sanphamModel> response) {
                    sanphamModel serverResponse = response.body();
                    if (serverResponse != null) {
                        if (serverResponse.isSuccess()) {
                            Toast.makeText(getApplicationContext(),"Thành Công",Toast.LENGTH_LONG).show();
                            edttenanh.setText(serverResponse.getName());
                        } else {
                            Toast.makeText(getApplicationContext(),"Thất Bại",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.v("Response", serverResponse.toString());
                    }
                }

                @Override
                public void onFailure(Call<sanphamModel> call, Throwable t) {
                    Log.d("TAG", t.getMessage());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "lỗi camera");
        }
    }

    private String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }

        return result;
    }

}