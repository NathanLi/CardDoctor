package com.yunkahui.datacubeper.home.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.ShareUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QrShareActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBgQr;
    private ImageView mIvQr;
    private TextView mTvCode;
    private Integer[] images;
    private Integer[] colors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_qr_share);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        images =getQrImage();
        colors = new Integer[]{Color.parseColor("#6f82ec"), Color.parseColor("#0e0507"), Color.parseColor("#ffcc01"), Color.parseColor("#7070e3"), Color.parseColor("#ffef75")};
        mTvCode.setText(String.format("邀请码：%s", getIntent().getStringExtra("code")));
        changeImgBg();
        Glide.with(this).load(DataUtils.getInfo().getUser_qrcode_img()).into(mIvQr);
    }

    private void initView() {
        mBgQr = findViewById(R.id.bg_qr);
        mIvQr = findViewById(R.id.iv_qr);
        mTvCode = findViewById(R.id.tv_code);
        mBgQr.setOnClickListener(this);
        findViewById(R.id.tv_share).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.show_tool);
        toolbar.setTitle("二维码分享");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //读取本地json,获取分享图片名字
    private Integer[] getQrImage() {
        String qrs = StringUtils.getJsonForLocation(this, "qr_share.json");
        try {
            JSONArray array = new JSONArray(qrs);
            List<Integer> images = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                images.add(getResources().getIdentifier(array.getString(i), "mipmap", getPackageName()));
            }
            return images.toArray(new Integer[]{});
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Integer[]{};
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bg_qr:
                changeImgBg();
                break;
            case R.id.tv_share:
                try {
                    View contentView = findViewById(R.id.rl_layout);
                    findViewById(R.id.tv_share).setVisibility(View.INVISIBLE);
                    Bitmap bitmap = Bitmap.createBitmap(contentView.getWidth(), contentView.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    contentView.draw(canvas);
                    findViewById(R.id.tv_share).setVisibility(View.VISIBLE);
                    File fileDir = getExternalFilesDir(null);
                    fileDir.mkdirs();
                    File file = new File(fileDir, "share.png");
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                    ShareUtils.share(this, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void changeImgBg() {
        int randomNumber = (int) (Math.random() * images.length);
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(colors[randomNumber]);
        }
        mBgQr.setBackgroundResource(images[randomNumber]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
