package com.yunkahui.datacubeper.applypos.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.UpLoadImageLogic;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.ImageCompress;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * POS 管理 更改结算信息上传图片页面
 */
public class UpLoadSettleImageFragment extends Fragment implements View.OnClickListener {

    private final int RESULT_CODE_IMAGE = 1001;
    private final int TYPE_IMAGE_ID_CARD = 105;
    private final int TYPE_IMAGE_BANK_CARD = 106;
    private final int TYPE_IMAGE_HAND_BANK_CARD = 107;

    private TextView mTextViewTitle;
    private TextView mTextViewMessage;
    private ImageView mImageViewImage;

    private String mImagePath;
    private int mType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_load_settle_image, container, false);

        mTextViewTitle = view.findViewById(R.id.text_view_title);
        mTextViewMessage = view.findViewById(R.id.text_view_message);
        mImageViewImage = view.findViewById(R.id.image_view_image);

        mImageViewImage.setOnClickListener(this);
        view.findViewById(R.id.button_submit).setOnClickListener(this);
        initData();
        return view;
    }


    private void initData() {
        mType = getArguments().getInt("type", 0);
        mImagePath=getArguments().getString("image");
        LogUtils.e("mImagePath="+mImagePath);
        GlideApp.with(this).load(BaseUrl.HOME+mImagePath).into(mImageViewImage);
        switch (mType) {
            case TYPE_IMAGE_ID_CARD:
                mTextViewTitle.setText("请上传第二代有效身份证正面照片");
                mTextViewMessage.setText("身份证正面照片");
                break;
            case TYPE_IMAGE_BANK_CARD:
                mTextViewTitle.setText("请上传银行卡正面照片");
                mTextViewMessage.setText("银行卡正面照片");
                break;
            case TYPE_IMAGE_HAND_BANK_CARD:
                mTextViewTitle.setText("请上传本人手持银行卡正面照片");
                mTextViewMessage.setText("本人手持银行卡正面照片");
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == RESULT_CODE_IMAGE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String path = images.get(0).path;
                switch (mType) {
                    case TYPE_IMAGE_ID_CARD:
                        compress("0",path);
                        break;
                    case TYPE_IMAGE_BANK_CARD:
                        compress("3",path);
                        break;
                    case TYPE_IMAGE_HAND_BANK_CARD:
                        compress("6",path);
                        break;
                }
                GlideApp.with(this).load(path).into(mImageViewImage);
            }
        }
    }

    //压缩图片
    private void compress(final String type, String path) {
        LoadingViewDialog.getInstance().show(getActivity());
        ImageCompress.compress(path, new ImageCompress.onCompressListener() {
            @Override
            public void onFinish(String path) {
                upLoadImageFile(type, path);
            }
        });
    }

    //上传图片
    private void upLoadImageFile(final String type, String path) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.show(getActivity(), "图片文件获取失败", Toast.LENGTH_SHORT);
            LoadingViewDialog.getInstance().dismiss();
            return;
        }
        LoadingViewDialog.getInstance().show(getActivity());
        new UpLoadImageLogic().upLoadImageFile(getActivity(), type, file, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("上传图片->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        mImagePath = object.optJSONObject("respData").optString("url");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "图片上传失败");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (TextUtils.isEmpty(mImagePath)) {
                    ToastUtils.show(getActivity(), "请选择图片");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("image", mImagePath);
                getActivity().setResult(getActivity().RESULT_OK, intent);
                getActivity().finish();
                break;
            case R.id.image_view_image:
                ImagePicker.getInstance().setSelectLimit(1);
                startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), RESULT_CODE_IMAGE);
                break;
        }
    }
}
