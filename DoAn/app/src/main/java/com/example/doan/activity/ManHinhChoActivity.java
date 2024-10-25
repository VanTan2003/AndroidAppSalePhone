package com.example.doan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.doan.R;

import io.paperdb.Paper;

public class ManHinhChoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cho);
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                }catch(Exception ex){

                }
                finally {
                    Intent intent=new Intent(getApplicationContext(),DangNhapActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}