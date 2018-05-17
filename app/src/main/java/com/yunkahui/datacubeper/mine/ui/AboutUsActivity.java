package com.yunkahui.datacubeper.mine.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pgyersdk.update.PgyUpdateManager;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.ShareUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AboutUsActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_us);
        super.onCreate(savedInstanceState);
        setTitle("关于我们");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        findViewById(R.id.rl_share_app).setOnClickListener(this);
        findViewById(R.id.rl_check_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_share_app:
                FileOutputStream outputStream = null;
                try {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_download_qr);
                    File fileDir = getExternalFilesDir(null);
                    fileDir.mkdirs();
                    File file = new File(fileDir, "share.png");
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                    ShareUtils.share(this,file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_check_update:
                PgyUpdateManager.register(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
    }
}
