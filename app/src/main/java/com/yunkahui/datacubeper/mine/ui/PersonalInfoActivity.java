package com.yunkahui.datacubeper.mine.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.PersonalInfo;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.MenuSimpleItemView;
import com.yunkahui.datacubeper.mine.logic.MineLogic;

import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class PersonalInfoActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE_IMAGE=1001;

    private ImageView mImageViewAvatar;
    private MenuSimpleItemView mSimpleItemViewNickName;
    private MenuSimpleItemView mSimpleItemViewAccount;
    private MenuSimpleItemView mSimpleItemViewQrCode;

    private MineLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_info);
        super.onCreate(savedInstanceState);
        setTitle("个人资料");

    }

    @Override
    public void initView() {
        mImageViewAvatar = findViewById(R.id.image_view_avatar);
        mSimpleItemViewNickName = findViewById(R.id.menu_simple_item_nick_name);
        mSimpleItemViewAccount = findViewById(R.id.menu_simple_item_account);
        mSimpleItemViewQrCode = findViewById(R.id.menu_simple_item_qr_code);

        mImageViewAvatar.setOnClickListener(this);
        mSimpleItemViewQrCode.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mLogic=new MineLogic();
        PersonalInfo info = DataUtils.getInfo();
        GlideApp.with(this).load(info.getAvatar()).error(R.mipmap.ic_header_normal).into(mImageViewAvatar);
        mSimpleItemViewNickName.setSubTitle(info.getNickname());
        mSimpleItemViewAccount.setSubTitle(info.getUser_mobile());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == RESULT_CODE_IMAGE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(images.size()>0){
                    upLoadAvatar(images.get(0).path);
                }

            }
        }

    }

    /**
     * 上传头像
     */
    private void upLoadAvatar(final String path){
        LoadingViewDialog.getInstance().show(PersonalInfoActivity.this);
        mLogic.upLoadAvatar(PersonalInfoActivity.this,path, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("上传头像->"+jsonObject.toString());
                try {
                    JSONObject object=new JSONObject(jsonObject.toString());
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        //TODO  保存上传头像返回的路径

                        GlideApp.with(PersonalInfoActivity.this).load(path).error(R.mipmap.ic_header_normal)
                                .transform(new CropCircleTransformation())
                                .into(mImageViewAvatar);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"上传头像失败");
            }
        });
    }


    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_avatar:
                ImagePicker.getInstance().setSelectLimit(1);
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, RESULT_CODE_IMAGE);
                break;
            case R.id.menu_simple_item_qr_code:
                break;
        }
    }
}
