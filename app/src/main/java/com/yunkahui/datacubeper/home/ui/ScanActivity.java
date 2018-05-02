package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yunkahui.datacubeper.R;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scan);
        super.onCreate(savedInstanceState);
        setTitle("二维码扫描");
    }
}
