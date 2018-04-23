package com.yunkahui.datacubeper.applypos.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

//上传本人手持身份证正面照片
public class UpLoadHandIdCardFragment extends Fragment implements View.OnClickListener {

    private final int RESULT_CODE_IMAGE_FRONT = 1001;

    private ImageView mImageViewFront;
    private String mFront;

    private UpLoadImageLogic mLogic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_up_load_hand_id_card,container,false);
        mImageViewFront=view.findViewById(R.id.image_view_hand_id_card_front);

        view.findViewById(R.id.button_submit).setOnClickListener(this);
        mImageViewFront.setOnClickListener(this);

        mLogic=new UpLoadImageLogic();
        loadData();
        return view;
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
        Glide.with(getActivity()).load(respData.getHand_identity_front()).thumbnail(0.1f).into(mImageViewFront);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && (requestCode == RESULT_CODE_IMAGE_FRONT)) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                switch (requestCode) {
                    case RESULT_CODE_IMAGE_FRONT:
                        String path1 = images.get(0).path;
                        upLoadImageFile("2", path1);
                        GlideApp.with(this).load(path1).into(mImageViewFront);
                        break;
                }
            }
        }
    }

    //上传文件
    private void upLoadImageFile(final String type, String path) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.show(getActivity(), "图片文件获取失败", Toast.LENGTH_SHORT);
            return;
        }
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.upLoadImageFile(getActivity(), type, file, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("上传图片->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object =baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        mFront = object.optJSONObject("respData").optString("url");
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
            objectFront.put("type", "2");
            objectFront.put("url", mFront);
            jsonArray.put(objectFront);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.commitSaveImage(getActivity(), jsonArray.toString(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                try {
                    LoadingViewDialog.getInstance().dismiss();
                    LogUtils.e("手持身份证提交->" + baseBean.getJsonObject().toString());
                    JSONObject object =baseBean.getJsonObject();
                    ToastUtils.show(getActivity(),object.optString("respDesc"));
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
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
                ToastUtils.show(getActivity(),"请求失败 "+throwable.toString());
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_hand_id_card_front:
                ImagePicker.getInstance().setSelectLimit(1);
                startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), RESULT_CODE_IMAGE_FRONT);
                break;
            case R.id.button_submit:
                if(TextUtils.isEmpty(mFront)){
                    ToastUtils.show(getActivity(),"请完善信息");
                    return;
                }
                submit();
                break;
        }
    }
}
