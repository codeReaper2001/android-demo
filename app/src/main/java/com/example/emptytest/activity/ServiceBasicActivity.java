package com.example.emptytest.activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytest.R;
import com.example.emptytest.service.BasicService;

public class ServiceBasicActivity extends AppCompatActivity implements View.OnClickListener {

    BasicService.MyBinder binder = null; // 可能为空

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);

        Button serviceStart = findViewById(R.id.service_basic_start);
        serviceStart.setOnClickListener(this);

        Button serviceBind = findViewById(R.id.service_basic_bind);
        serviceBind.setOnClickListener(this);

        Button serviceBindSet = findViewById(R.id.service_basic_bind_set);
        serviceBindSet.setOnClickListener(this);

        Button serviceBindGet = findViewById(R.id.service_basic_bind_get);
        serviceBindGet.setOnClickListener(this);

        Button serviceUnbind = findViewById(R.id.service_basic_unbind);
        serviceUnbind.setOnClickListener(this);

        Button serviceStop = findViewById(R.id.service_basic_stop);
        serviceStop.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_basic_start:
                startService();
                break;
            case R.id.service_basic_stop:
                Intent intent = new Intent(this, BasicService.class);
                stopService(intent);
                break;
            case R.id.service_basic_bind:
                bindService();
                break;
            case R.id.service_basic_bind_get:
                getBindInfo();
                break;
        }
    }

    private void startService() {
        Intent intent = new Intent(this, BasicService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent(this, BasicService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected");
                binder = (BasicService.MyBinder) service; // 连接完成后，binder赋值到外层的binder，进行数据传输等
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected");
            }
        }, Service.BIND_AUTO_CREATE);
    }

    private void getBindInfo() {
        if (binder == null) {
            Log.i(TAG, "binder is null");
            return;
        }
        Log.i(TAG, "getBindInfo: " + binder.getInfo());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
