package com.yunkahui.datacubeper.login.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class SplashActivity extends AppCompatActivity implements IActivityStatusBar{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        new InnerThread().start();
    }

    Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            return true;
        }
    });

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getStatusBarColor() {
        return 0;
    }


    class InnerThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message.obtain(mHandler).sendToTarget();
        }
    }

}
