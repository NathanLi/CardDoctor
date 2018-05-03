package com.yunkahui.datacubeper.home.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.TintUtils;
import com.yunkahui.datacubeper.share.ui.WebViewActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.regex.Pattern;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

public class ScanActivity extends AppCompatActivity implements IActivityStatusBar, QRCodeView.Delegate, View.OnClickListener {

    private final int RESULT_CODE_IMAGE_QR = 101;

    private QRCodeView mQRCodeView;
    private TextView mTvFlash;

    private SensorManager sensorManager;
    private boolean mIsOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scan);
        super.onCreate(savedInstanceState);
        setTitle("二维码扫描");
        mQRCodeView.startSpot();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "相册").setIcon(R.mipmap.ic_album).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivityForResult(new Intent(this, ImageGridActivity.class), RESULT_CODE_IMAGE_QR);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == RESULT_CODE_IMAGE_QR) {
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                new InnerAsyncTask(this).execute(images.get(0).path);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);

        mTvFlash.setText("轻点打开");
        mTvFlash.setTextColor(Color.WHITE);
        mTvFlash.setCompoundDrawablesWithIntrinsicBounds(null, TintUtils.tintDrawable(getResources().getDrawable(R.mipmap.ic_flash),
                ColorStateList.valueOf(getResources().getColor(R.color.white))), null, null);
    }

    @Override
    public void initView() {
        mQRCodeView = findViewById(R.id.zxingview);
        mTvFlash = findViewById(R.id.tv_flash);
        mQRCodeView.setDelegate(this);
        mTvFlash.setOnClickListener(this);
    }

    //******** 扫描成功回调 ********
    @Override
    public void onScanQRCodeSuccess(String result) {
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~/])+$");
        if (pattern.matcher(result).matches()) {
            startActivity(new Intent(this, WebViewActivity.class).putExtra("title", "提示").putExtra("url", result));
        } else {
            startActivity(new Intent(this, ScanResultActivity.class).putExtra("result", result));
        }
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_flash:
                if (mIsOpen) {
                    mQRCodeView.closeFlashlight();
                    mTvFlash.setText("轻点打开");
                    mTvFlash.setTextColor(Color.WHITE);
                    mTvFlash.setCompoundDrawablesWithIntrinsicBounds(null, TintUtils.tintDrawable(getResources().getDrawable(R.mipmap.ic_flash),
                            ColorStateList.valueOf(getResources().getColor(R.color.white))), null, null);
                } else {
                    mQRCodeView.openFlashlight();
                    mTvFlash.setText("轻点关闭");
                    mTvFlash.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mTvFlash.setCompoundDrawablesWithIntrinsicBounds(null, TintUtils.tintDrawable(getResources().getDrawable(R.mipmap.ic_flash),
                            ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary))), null, null);
                }
                mIsOpen = !mIsOpen;
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(true);
        imagePicker.setCrop(true);
        mQRCodeView.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
        super.onDestroy();
    }

    private SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float value = event.values[0];
            if (value < 10) {
                mTvFlash.setVisibility(View.VISIBLE);
            }
        }
    };

    private static class InnerAsyncTask extends AsyncTask<String, Integer, String> {

        WeakReference<ScanActivity> mReference;

        InnerAsyncTask(ScanActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... strings) {
            for (String s : strings) {
                return QRCodeDecoder.syncDecodeQRCode(s);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (mReference.get() != null) {
                mReference.get().onResultForParsingQr(result);
            }
            super.onPostExecute(result);
        }
    }

    public void onResultForParsingQr(String result) {
        if (TextUtils.isEmpty(result)) {
            Toast.makeText(ScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ScanActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
