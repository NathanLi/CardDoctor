package com.yunkahui.datacubeper.applypos.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.yunkahui.datacubeper.common.view.SimpleMenuItemView;

import org.json.JSONObject;

/**
 * 申请POS第二步
 */
public class ApplyPosSecondFragment extends Fragment implements View.OnClickListener {

    public final int RESULT_CODE_UPDATE = 1001;


    private ProgressApplyPosView mProgressApplyPosView;
    private SimpleMenuItemView mMenuItemViewPosMail;
    private SimpleMenuItemView mMenuItemViewTerminalInfo;
    private SimpleMenuItemView mMenuItemViewSettleInfo;
    private SimpleMenuItemView mMenuItemViewIdCard;
    private SimpleMenuItemView mMenuItemViewHandIdCard;
    private SimpleMenuItemView mMenuItemViewBankCard;


    private ApplyPosLogic mLogic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_pos_second, container, false);

        mProgressApplyPosView = view.findViewById(R.id.progress_bar_apply_pos);
        mMenuItemViewPosMail = view.findViewById(R.id.simple_menu_pos_mail);
        mMenuItemViewTerminalInfo = view.findViewById(R.id.simple_menu_terminal_info);
        mMenuItemViewSettleInfo = view.findViewById(R.id.simple_menu_settle_info);
        mMenuItemViewIdCard = view.findViewById(R.id.simple_menu_id_card_photo);
        mMenuItemViewHandIdCard = view.findViewById(R.id.simple_menu_hand_id_card_photo);
        mMenuItemViewBankCard = view.findViewById(R.id.simple_menu_bank_card_photo);

        view.findViewById(R.id.button_submit).setOnClickListener(this);
        mMenuItemViewPosMail.setOnClickListener(this);
        mMenuItemViewTerminalInfo.setOnClickListener(this);
        mMenuItemViewSettleInfo.setOnClickListener(this);
        mMenuItemViewIdCard.setOnClickListener(this);
        mMenuItemViewHandIdCard.setOnClickListener(this);
        mMenuItemViewBankCard.setOnClickListener(this);
        mProgressApplyPosView.setProgress(2);

        mLogic = new ApplyPosLogic();
        checkPosApplyUploadData();
        return view;
    }

    //查询已上传的资料
    private void checkPosApplyUploadData() {
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.checkPosApplyUploadData(getActivity(), new SimpleCallBack<BaseBean<PosApplyInfo>>() {
            @Override
            public void onSuccess(BaseBean<PosApplyInfo> bean) {
                LoadingViewDialog.getInstance().dismiss();
                if (RequestUtils.SUCCESS.equals(bean.getRespCode())) {
                    LogUtils.e("pos已上传资料->" + bean.getJsonObject().toString());
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

    //更新数据，查看哪部分数据已上传
    private void updateData(PosApplyInfo info) {
        if (!TextUtils.isEmpty(info.getReceive_name()) && !TextUtils.isEmpty(info.getReceive_phone()) && !TextUtils.isEmpty(info.getReceive_address())) {
            mMenuItemViewPosMail.setRightIcon(R.mipmap.ic_icon_radio_select);
        }
        if (!TextUtils.isEmpty(info.getLegal_name()) && !TextUtils.isEmpty(info.getLegal_identity_num()) && !TextUtils.isEmpty(info.getLegal_phone())
                && !TextUtils.isEmpty(info.getLegal_province()) && !TextUtils.isEmpty(info.getLegal_city()) && !TextUtils.isEmpty(info.getManage_address())) {
            mMenuItemViewTerminalInfo.setRightIcon(R.mipmap.ic_icon_radio_select);
        }
        if (!TextUtils.isEmpty(info.getBank_card_num()) && !TextUtils.isEmpty(info.getBank_card_name()) && !TextUtils.isEmpty(info.getDeposit_province())
                && !TextUtils.isEmpty(info.getDeposit_city()) && !TextUtils.isEmpty(info.getDeposit_bank()) && !TextUtils.isEmpty(info.getCouplet_num())) {
            mMenuItemViewSettleInfo.setRightIcon(R.mipmap.ic_icon_radio_select);
        }
        if (!TextUtils.isEmpty(info.getIdentity_front_img()) && !TextUtils.isEmpty(info.getIdentity_back_img())) {
            mMenuItemViewIdCard.setRightIcon(R.mipmap.ic_icon_radio_select);
        }
        if (!TextUtils.isEmpty(info.getHand_identity_front())) {
            mMenuItemViewHandIdCard.setRightIcon(R.mipmap.ic_icon_radio_select);
        }
        if (!TextUtils.isEmpty(info.getBank_card_front()) && !TextUtils.isEmpty(info.getBank_card_back())) {
            mMenuItemViewBankCard.setRightIcon(R.mipmap.ic_icon_radio_select);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK && requestCode == RESULT_CODE_UPDATE) {
            checkPosApplyUploadData();
        }
    }

    //把POS申请审核状态由已付款修改为审核中
    private void changeApplyStatus() {
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.changePosApplyStatus(getActivity(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    LogUtils.e("pos提交资料->" + baseBean.getJsonObject().toString());
                    JSONObject object = baseBean.getJsonObject();
                    ToastUtils.show(getActivity(), object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        ((ApplyPosActivity) getActivity()).startToPosDisposeRunning();
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
            case R.id.button_submit:
                changeApplyStatus();
                break;
            case R.id.simple_menu_pos_mail:
                startActivityForResult(new Intent(getActivity(), PosMailInfoActivity.class), RESULT_CODE_UPDATE);
                break;
            case R.id.simple_menu_terminal_info:
                startActivityForResult(new Intent(getActivity(), TerminalInfoActivity.class), RESULT_CODE_UPDATE);
                break;
            case R.id.simple_menu_settle_info:
                startActivityForResult(new Intent(getActivity(), SettleInfoActivity.class), RESULT_CODE_UPDATE);
                break;
            case R.id.simple_menu_id_card_photo:
                Intent intentIdCard = new Intent(getActivity(), UpLoadImageActivity.class);
                intentIdCard.putExtra("type", UpLoadImageActivity.TYPE_ID_CARD);
                startActivityForResult(intentIdCard, RESULT_CODE_UPDATE);
                break;
            case R.id.simple_menu_hand_id_card_photo:
                Intent intentHandIdCard = new Intent(getActivity(), UpLoadImageActivity.class);
                intentHandIdCard.putExtra("type", UpLoadImageActivity.TYPE_HAND_ID_CARD);
                startActivityForResult(intentHandIdCard, RESULT_CODE_UPDATE);
                break;
            case R.id.simple_menu_bank_card_photo:
                Intent intentBankCard = new Intent(getActivity(), UpLoadImageActivity.class);
                intentBankCard.putExtra("type", UpLoadImageActivity.TYPE_BANK_CARD);
                startActivityForResult(intentBankCard, RESULT_CODE_UPDATE);
                break;
        }
    }
}
