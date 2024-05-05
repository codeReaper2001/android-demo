package com.example.emptytest.service;

import static android.content.ContentValues.TAG;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class BasicService extends Service {
    private int binderCount;

    public class MyBinder extends Binder {
        public int getInfo() {
            return  binderCount;
        }
        public void setInfo(int info) {
            binderCount = info;
        }
    }

    private List<Thread> threads = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: BasicService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        Thread thread = new Thread() {
            int count = 0;

            @Override
            public void run() {
                super.run();
                while (true) {
                    Formatter msg = new Formatter().format("threadName: %s, count: %d", getName(), count);
                    Log.i(TAG, msg.toString());
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    count++;
                    binderCount = count;
                }
            }
        };
        thread.start();
        threads.add(thread);
        return  super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: BasicService");
        for (Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
}
