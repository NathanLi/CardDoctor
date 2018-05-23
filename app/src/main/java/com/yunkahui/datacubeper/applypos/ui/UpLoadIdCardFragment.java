package com.yunkahui.datacubeper.applypos.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.ApplyPosLogic;
import com.yunkahui.datacubeper.applypos.logic.UpLoadImageLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PosApplyInfo;
import com.yunkahui.datacubeper.common.utils.ImageCompress;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * 上传身份证正反面照片
 */
public class UpLoadIdCardFragment extends Fragment implements View.OnClickListener {

    private final int RESULT_CODE_IMAGE_FRONT = 1001;
    private final int RESULT_CODE_IMAGE_BACK = 1002;

    private ImageView mImageViewFront;
    private ImageView mImageViewBack;

    private UpLoadImageLogic mLogic;
    private String mFront;
    private String mBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_load_id_card, container, false);
        mImageViewFront = view.findViewById(R.id.image_view_id_card_front);
        mImageViewBack = view.findViewById(R.id.image_view_id_card_back);

        view.findViewById(R.id.button_submit).setOnClickListener(this);
        mImageViewFront.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);

        mLogic = new UpLoadImageLogic();
        loadData();
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && (requestCode == RESULT_CODE_IMAGE_FRONT || requestCode == RESULT_CODE_IMAGE_BACK)) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                switch (requestCode) {
                    case RESULT_CODE_IMAGE_FRONT:
                        String path1 = images.get(0).path;
                        upLoadImageFile("0", path1);
                        GlideApp.with(this).load(path1).into(mImageViewFront);
                        compress("0", path1);
                        break;
                    case RESULT_CODE_IMAGE_BACK:
                        String path2 = images.get(0).path;
//                        upLoadImageFile("1", path2);
                        GlideApp.with(this).load(path2).into(mImageViewBack);
                        compress("1", path2);
                        break;
                }
            }
        }
    }

    //查询已上传数据
    private void loadData() {
        LoadingViewDialog.getInstance().show(getActivity());
        new ApplyPosLogic().checkPosApplyUploadData(getActivity(), new SimpleCallBack<BaseBean<PosApplyInfo>>() {
            @Override
            public void onSuccess(BaseBean<PosApplyInfo> bean) {
                LoadingViewDialog.getInstance().dismiss();
                if (RequestUtils.SUCCESS.equals(bean.getRespCode())) {
                    updateData(bean.getRespData());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
            }
        });

    }

    //更新数据
    private void updateData(PosApplyInfo respData) {
        Glide.with(getActivity()).load(respData.getIdentity_front_img()).thumbnail(0.1f).into(mImageViewFront);
        Glide.with(getActivity()).load(respData.getIdentity_back_img()).thumbnail(0.1f).into(mImageViewBack);
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

    //上传文件
    private void upLoadImageFile(final String type, String path) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.show(getActivity(), "图片文件获取失败", Toast.LENGTH_SHORT);
            LoadingViewDialog.getInstance().dismiss();
            return;
        }
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.upLoadImageFile(getActivity(), type, file, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("上传图片->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        if ("0".equals(type)) {
                            mFront = object.optJSONObject("respData").optString("url");
                        } else if ("1".equals(type)) {
                            mBack = object.optJSONObject("respData").optString("url");
                        }
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

    //提交保存上传的图片
    private void submit() {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject objectFront = new JSONObject();
            objectFront.put("type", "0");
            objectFront.put("url", mFront);
            JSONObject objectBack = new JSONObject();
            objectBack.put("type", "1");
            objectBack.put("url", mBack);
            jsonArray.put(objectFront);
            jsonArray.put(objectBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.commitSaveImage(getActivity(), jsonArray.toString(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                try {
                    LoadingViewDialog.getInstance().dismiss();
                    LogUtils.e("身份证提交->" + baseBean.getJsonObject().toString());
                    JSONObject object = baseBean.getJsonObject();
                    ToastUtils.show(getActivity(), object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        getActivity().setResult(getActivity().RESULT_OK);
                        getActivity().finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_id_card_front:
                ImagePicker.getInstance().setSelectLimit(1);
                startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), RESULT_CODE_IMAGE_FRONT);
                break;
            case R.id.image_view_id_card_back:
                ImagePicker.getInstance().setSelectLimit(1);
                startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), RESULT_CODE_IMAGE_BACK);
                break;
            case R.id.button_submit:
                if (TextUtils.isEmpty(mFront) || TextUtils.isEmpty(mBack)) {
                    ToastUtils.show(getActivity(), "请先完善信息");
                    return;
                }
                submit();
                break;
        }

    }
}
