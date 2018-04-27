package com.yunkahui.datacubeper.login.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityBase;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity implements IActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        new InnerThread(this).start();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private static class InnerThread extends Thread {

        WeakReference<SplashActivity> mReference;

        InnerThread(SplashActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            SplashActivity activity = mReference.get();
            if (activity != null) {
                activity.sendMsg();
            }
        }
    }

    public void sendMsg() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Looper.prepare();
        new InnerHandler(SplashActivity.this).obtainMessage().sendToTarget();
        Looper.loop();
    }

    public void startLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    private static class InnerHandler extends Handler {

        WeakReference<SplashActivity> mReference;

        InnerHandler(SplashActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mReference.get();
            if (activity != null) {
                activity.startLogin();
            }
        }
    }

}
