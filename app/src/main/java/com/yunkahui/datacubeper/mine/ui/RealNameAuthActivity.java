package com.yunkahui.datacubeper.mine.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.ImageCompress;
import com.yunkahui.datacubeper.common.utils.SharedPreferencesUtils;
import com.yunkahui.datacubeper.common.view.chart.DealInterface;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.mine.logic.RealNameAuthLogic;

import org.json.JSONObject;

import java.util.ArrayList;

//实名认证
public class RealNameAuthActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE_IMAGE_FRONT = 1001;
    private final int RESULT_CODE_IMAGE_BACK = 1002;

    private RelativeLayout mRelativeLayoutIdCardFront;
    private RelativeLayout mRelativeLayoutIdCardBack;
    private ImageView mImageViewIdCardFront;
    private ImageView mImageViewIdCardBack;

    private String mFront;
    private String mBack;
    private String mRealName;
    private String mIdCardNumber;

    private RealNameAuthLogic mLogic;
    private boolean mIsRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_real_name_auth);
        super.onCreate(savedInstanceState);
        setTitle("实名认证");

    }

    @Override
    public void initData() {
        mLogic = new RealNameAuthLogic();
    }

    @Override
    public void initView() {

        mRelativeLayoutIdCardFront = findViewById(R.id.relative_layout_id_card_front);
        mRelativeLayoutIdCardBack = findViewById(R.id.relative_layout_id_card_back);
        mImageViewIdCardFront = findViewById(R.id.image_view_id_card_front);
        mImageViewIdCardBack = findViewById(R.id.image_view_id_card_back);

        mRelativeLayoutIdCardFront.setOnClickListener(this);
        mRelativeLayoutIdCardBack.setOnClickListener(this);
        findViewById(R.id.button_submit).setOnClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && (requestCode == RESULT_CODE_IMAGE_FRONT || requestCode == RESULT_CODE_IMAGE_BACK)) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                switch (requestCode) {
                    case RESULT_CODE_IMAGE_FRONT:
                        GlideApp.with(this).load(images.get(0).path).into(mImageViewIdCardFront);
                        ImageCompress.compress(images.get(0).path, new ImageCompress.onCompressListener() {
                            @Override
                            public void onFinish(String path) {
                                mFront = path;
                            }
                        });

                        break;
                    case RESULT_CODE_IMAGE_BACK:
                        GlideApp.with(this).load(images.get(0).path).into(mImageViewIdCardBack);
                        ImageCompress.compress(images.get(0).path, new ImageCompress.onCompressListener() {
                            @Override
                            public void onFinish(String path) {
                                mBack = path;
                            }
                        });
                        break;
                }
            }
        }
    }

    //解析身份证获取文字信息
    private void parsingIdCard() {
        if (TextUtils.isEmpty(mFront) || TextUtils.isEmpty(mBack)) {
            ToastUtils.show(this, "请完善信息");
            return;
        }
        LoadingViewDialog.getInstance().show(this);
        mLogic.parsingIdCard(((BitmapDrawable) mImageViewIdCardFront.getDrawable()).getBitmap(), new DealInterface<String>() {
            @Override
            public void success(final String object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LoadingViewDialog.getInstance().dismiss();
                            LogUtils.e("身份-->" + object);
                            JSONObject messObject = new JSONObject(object);
                            String dataValue = messObject.optJSONArray("outputs").optJSONObject(0).optJSONObject("outputValue").optString("dataValue");
                            JSONObject dataValueObject = new JSONObject(dataValue);
                            mRealName = dataValueObject.optString("name");
                            mIdCardNumber = dataValueObject.optString("num");
                            LogUtils.e("mRealName=" + mRealName);
                            LogUtils.e("mIdCardNumber=" + mIdCardNumber);
                            if (TextUtils.isEmpty(mRealName) || TextUtils.isEmpty(mIdCardNumber)) {
                                ToastUtils.show(getApplicationContext(), "身份证照片检验失败，请重新上传");
                            } else {
                                if (!mIsRunning) {
                                    mIsRunning = true;
                                    showAuthDialog();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void failure(final String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingViewDialog.getInstance().dismiss();
                        int i = SharedPreferencesUtils.getInt(RealNameAuthActivity.this, DataUtils.getInfo().getUser_mobile());
                        SharedPreferencesUtils.save(RealNameAuthActivity.this, DataUtils.getInfo().getUser_mobile(), i + 1);
                        ToastUtils.show(getApplicationContext(), "身份证照片检验失败，请重新上传");
                        LogUtils.e("身份错误-->" + error);
                    }
                });
            }
        });

    }

    //显示识别出身份证信息数据的弹窗
    public void showAuthDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("信息校验")
                .setMessage("姓名：" + mRealName + "\n身份证号码：" + mIdCardNumber)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitRealNameAuthImage();
                        mIsRunning = false;
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIsRunning = false;
                    }
                })
                .create();
        dialog.show();
    }

    //提交身份照片
    private void submitRealNameAuthImage() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.submitRealNameAuthImage(this, mFront, mBack, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                try {
                    LogUtils.e("实名认证1-->" + baseBean.getJsonObject().toString());
                    JSONObject object = baseBean.getJsonObject();
//                    ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        submitRealNameAuthInfo();
                    } else {
                        LoadingViewDialog.getInstance().dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "实名认证请求失败->" + throwable.toString());
                mIsRunning = false;
            }
        });
    }

    //提交身份证数据
    public void submitRealNameAuthInfo() {
        mLogic.submitRealNameAuthInfo(this, mRealName, mIdCardNumber, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                try {
                    LoadingViewDialog.getInstance().dismiss();
                    LogUtils.e("实名认证2-->" + baseBean.getJsonObject().toString());
                    JSONObject object = baseBean.getJsonObject();
                    ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        mIsRunning = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "实名认证请求失败->" + throwable.toString());
                mIsRunning = false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_layout_id_card_front:
                ImagePicker.getInstance().setSelectLimit(1);
                startActivityForResult(new Intent(this, ImageGridActivity.class), RESULT_CODE_IMAGE_FRONT);
                break;
            case R.id.relative_layout_id_card_back:
                ImagePicker.getInstance().setSelectLimit(1);
                startActivityForResult(new Intent(this, ImageGridActivity.class), RESULT_CODE_IMAGE_BACK);
                break;
            case R.id.button_submit:
                if (SharedPreferencesUtils.getInt(this, DataUtils.getInfo().getUser_mobile()) > 3) {
                    setResult(RESULT_OK);
                    startActivity(new Intent(this, RealNameHandAuthActivity.class));
                } else {
                    parsingIdCard();
                }
                break;
        }
    }
}
