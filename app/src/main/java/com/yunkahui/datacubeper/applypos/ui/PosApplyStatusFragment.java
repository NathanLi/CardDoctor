package com.yunkahui.datacubeper.applypos.ui;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.ApplyPosLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PosApplyInfo;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * pos申请状态过度页面
 */
public class PosApplyStatusFragment extends Fragment implements View.OnClickListener {

    public static final int TYPE_DISPOSE_RUNNING=101;   //审核中
    public static final int TYPE_DISPOSE_FAIL=102;  //审核不过
    public static final int TYPE_DISPOSE_SUCCESS=103;   //审核通过
    public static final int TYPE_HAVE_MAIL=104;     //已邮寄
    public static final int TYPE_HAND_POS_DISPOSE_RUNNING=105;  //手持POS机照片审核中
    public static final int TYPE_HAND_POS_DISPOSE_SUCCESS=106;  //手持POS机照片审核通过（完成）
    public static final int TYPE_HAND_POS_DISPOSE_FAIL=107;     //手持POS机照片审核不通过


    private final int RESULT_CODE_HAND_POS=1001;

    private ProgressApplyPosView mProgressApplyPosView;
    private ImageView mImageViewIcon;
    private TextView mTextViewResult;
    private TextView mTextViewMessage;
    private Button mButtonSubmit;

    private int mType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pos_apply_status, container, false);
        mProgressApplyPosView=view.findViewById(R.id.progress_bar_apply_pos);
        mImageViewIcon=view.findViewById(R.id.image_view_dispose_result);
        mTextViewResult=view.findViewById(R.id.text_view_result);
        mTextViewMessage=view.findViewById(R.id.text_view_message);
        mButtonSubmit=view.findViewById(R.id.button_submit);

        mButtonSubmit.setOnClickListener(this);
        mType=getArguments().getInt("type");
        if(mType==TYPE_DISPOSE_FAIL){
            loadData();
        }else if(mType==TYPE_HAVE_MAIL){
            checkMailInfo();
        }
        initData();
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
                    mTextViewMessage.setText(bean.getRespData().getContent());
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
            }
        });
    }

    //查询邮寄信息
    private void checkMailInfo(){
        LoadingViewDialog.getInstance().show(getActivity());
        new ApplyPosLogic().checkHaveMailInfo(getActivity(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    LogUtils.e("pos邮寄信息->"+baseBean.getJsonObject().toString());
                    JSONObject object=baseBean.getJsonObject();
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        mTextViewResult.setText("POS机邮寄中\n"+object.optJSONObject("respData").optString("kd_company")+"\n快递单号："+object.optJSONObject("respData").optString("kd_company"));
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



    private void initData(){
        switch (mType){
            case TYPE_DISPOSE_RUNNING:
                mProgressApplyPosView.setProgress(3);
                mImageViewIcon.setImageResource(R.mipmap.ic_dispose_result);
                mTextViewResult.setText("资料提交成功");
                mTextViewMessage.setText("我们正在审核中，\n大概3-5个工作日审核完成，\n请耐心等待审核结果");
                break;
            case TYPE_DISPOSE_FAIL:
                mProgressApplyPosView.setProgress(3);
                mImageViewIcon.setImageResource(R.mipmap.ic_dispose_fail);
                mTextViewResult.setText("审核未通过");
                mTextViewMessage.setTextColor(Color.parseColor("#FF0000"));
                mButtonSubmit.setVisibility(View.VISIBLE);
                break;
            case TYPE_DISPOSE_SUCCESS:
                mProgressApplyPosView.setProgress(3);
                mImageViewIcon.setImageResource(R.mipmap.ic_dispose_result);
                mTextViewResult.setText("审核已通过");
                mTextViewMessage.setText("审核已通过，货物即将邮寄，请耐心等待");
                break;
            case TYPE_HAVE_MAIL:
                mProgressApplyPosView.setProgress(4);
                mImageViewIcon.setImageResource(R.mipmap.ic_dispose_result);
                mTextViewMessage.setTextColor(Color.parseColor("#FF0000"));
                mTextViewMessage.setText("注意：收到POS机后，\n请上传本人手持POS机照片");
                mButtonSubmit.setVisibility(View.VISIBLE);
                mButtonSubmit.setText("上传手持POS机照片");
                break;
            case TYPE_HAND_POS_DISPOSE_RUNNING:
                mProgressApplyPosView.setProgress(5);
                mImageViewIcon.setImageResource(R.mipmap.ic_dispose_result);
                mTextViewResult.setText("照片提交成功");
                mTextViewMessage.setText("我们正在审核中，\n大概1-2个工作日审核完成，\n请耐心等待审核结果");
                break;
            case TYPE_HAND_POS_DISPOSE_SUCCESS:
                mProgressApplyPosView.setProgress(5);
                mImageViewIcon.setImageResource(R.mipmap.ic_dispose_result);
                mTextViewResult.setText("POS开通成功");
                mTextViewMessage.setText("可在“我的-POS管理”查看到终端和结算信息");
                break;
            case TYPE_HAND_POS_DISPOSE_FAIL:
                mProgressApplyPosView.setProgress(5);
                mImageViewIcon.setImageResource(R.mipmap.ic_dispose_fail);
                mTextViewResult.setText("审核不通过");
                mButtonSubmit.setVisibility(View.VISIBLE);
                mButtonSubmit.setText("上传手持POS机照片");
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==getActivity().RESULT_OK&&requestCode==RESULT_CODE_HAND_POS){
            ((ApplyPosActivity)getActivity()).startToHandPosDisposeRunning();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                switch (mType){
                    case TYPE_DISPOSE_FAIL:
                        ((ApplyPosActivity)getActivity()).startToApplyPos();
                        break;
                    case TYPE_HAVE_MAIL:
                    case TYPE_HAND_POS_DISPOSE_FAIL:
                        Intent intent=new Intent(getActivity(),UpLoadImageActivity.class);
                        intent.putExtra("type",UpLoadImageActivity.TYPE_HAND_POS);
                        startActivityForResult(intent,RESULT_CODE_HAND_POS);
                        break;
                }
                break;
        }
    }
}
