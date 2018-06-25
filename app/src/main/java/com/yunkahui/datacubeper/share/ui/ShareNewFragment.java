package com.yunkahui.datacubeper.share.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.ClipboardManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.other.OENType;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.OnDoManyClickListener;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.DoubleBlockView;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.ui.QrShareActivity;
import com.yunkahui.datacubeper.share.logic.RecordType;
import com.yunkahui.datacubeper.share.logic.ShareLogic;

import org.json.JSONObject;


/**
 * 分享fragment (新)
 */
public class ShareNewFragment extends Fragment implements View.OnClickListener {

    private DoubleBlockView mDoubleBlockView1;
    private DoubleBlockView mDoubleBlockView2;
    private DoubleBlockView mDoubleBlockView3;
    private ShareLogic mShareLogic;
    private TextView mTvRestCode;
    private TextView mTvMyCode;
    private TextView mTextViewPolicy1;
    private TextView mTextViewPolicy2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_new, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initData() {
        mShareLogic = new ShareLogic();
        mTextViewPolicy1.setText(Html.fromHtml(getResources().getString(R.string.share_policy_1)));
        mTextViewPolicy2.setText(Html.fromHtml(getResources().getString(R.string.share_policy_2)));

        initListener();
        requestSharePageInfo();
    }

    //******** 获取分享页面数据 ********
    private void requestSharePageInfo() {
        mShareLogic.requestSharePageInfo(getActivity(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("分享页面->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    JSONObject object = baseBean.getJsonObject();
                    JSONObject respData = object.optJSONObject("respData");
                    mDoubleBlockView1.setLeftValue(respData.optString("userCommissions"))
                            .setRightValue(respData.optString("userFenruns"));
                    mDoubleBlockView2.setLeftValue(String.valueOf(respData.optInt("commonMemberCount")))
                            .setRightValue(String.valueOf(respData.optInt("vipMemberCount")));
                    mDoubleBlockView3.setLeftValue(respData.optString("posFenruns"))
                            .setRightValue(respData.optString("userPoints"));
                    mTvRestCode.setText(String.valueOf(respData.optInt("reNum")));
                    mTvMyCode.setText(respData.optString("userUniqueCode"));
                    DataUtils.setInvitateCode(respData.optString("userUniqueCode"));
                } else {
                    Toast.makeText(getActivity(), baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getActivity(), "获取分享页面数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        mDoubleBlockView1.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(DataUtils.getInfo().getVIP_status())) {
                    ToastUtils.show(getActivity(), "请先升级VIP");
                } else {
                    startActivity(new Intent(getActivity(), RecordListActivity.class)
                            .putExtra("type", RecordType.myWallet_all)
                            .putExtra("title", "分佣明细"));
                }
            }
        }).setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(DataUtils.getInfo().getVIP_status())) {
                    ToastUtils.show(getActivity(), "请先升级VIP");
                } else {
                    startActivity(new Intent(getActivity(), RecordListActivity.class)
                            .putExtra("type", RecordType.online_all)
                            .putExtra("title", "线上分润明细"));
                }
            }
        });
        mDoubleBlockView2.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MemberActivity.class).putExtra("isVip", false));
            }
        }).setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MemberActivity.class).putExtra("isVip", true));
            }
        });
        mDoubleBlockView3.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), PosFenRunListActivity.class));
                startActivity(new Intent(getActivity(), RecordListActivity.class)
                        .putExtra("type", RecordType.pos_all)
                        .putExtra("title", "POS分润明细"));
            }
        }).setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), IntegralRecordListActivity.class));
//                startActivity(new Intent(getActivity(),RecordListActivity.class)
//                        .putExtra("type", RecordType.integral_all)
//                        .putExtra("title", "积分明细"));
            }
        });
    }

    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.share));
        mDoubleBlockView1 = view.findViewById(R.id.double_block_view_1);
        mDoubleBlockView2 = view.findViewById(R.id.double_block_view_2);
        mDoubleBlockView3 = view.findViewById(R.id.double_block_view_3);
        mTvRestCode = view.findViewById(R.id.tv_rest_code);
        mTvMyCode = view.findViewById(R.id.tv_my_code);
        mTextViewPolicy1 = view.findViewById(R.id.text_view_share_policy_1);
        mTextViewPolicy2 = view.findViewById(R.id.text_view_share_policy_2);

        view.findViewById(R.id.btn_produce_code).setOnClickListener(this);
        view.findViewById(R.id.tv_link_share).setOnClickListener(this);
        view.findViewById(R.id.tv_qr_share).setOnClickListener(this);
        view.findViewById(R.id.tv_vip_course).setOnClickListener(this);
        toolbar.getRoot().setBackgroundResource(0);

        if (OENType.currentType() == OENType.xinyongdashi) {
            TextView textView = view.findViewById(R.id.text_view_share_policy_3);
            textView.setText(Html.fromHtml(getResources().getString(R.string.share_policy_3)));
            textView.setVisibility(View.VISIBLE);
        }
    }

    //激活码弹窗
    private void showActiveDialog(String code) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_activate_code, null);
        final TextView textView = view.findViewById(R.id.text_view_code);
        textView.setText(code);
        view.findViewById(R.id.button_submit).setOnClickListener(new OnDoManyClickListener() {
            @Override
            public void onDoManyClick(View view) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(textView.getText());
                ToastUtils.show(getActivity(), "已复制到剪切板");
            }
        });
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_produce_code:
                LoadingViewDialog.getInstance().show(getActivity());
                mShareLogic.createActivationCode(getActivity(), new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        LoadingViewDialog.getInstance().dismiss();
                        LogUtils.e("生成激活码->" + baseBean.getJsonObject().toString());
                        if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                            showActiveDialog(baseBean.getJsonObject().optString("respData"));
                        } else {
                            ToastUtils.show(getActivity(), baseBean.getRespDesc());
                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadingViewDialog.getInstance().dismiss();
                        Toast.makeText(getActivity(), "生成激活码失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.tv_link_share:
                startActivity(new Intent(getActivity(), WebViewActivity.class)
                        .putExtra("title", "链接分享")
                        .putExtra("url", "http://jpers.file.hangmuxitong.com/xinyongka/index.html?user_unique_code=" + mTvMyCode.getText().toString().trim() + "&org_number=" + getResources().getString(R.string.org_number)));
                break;
            case R.id.tv_qr_share:
                startActivity(new Intent(getActivity(), QrShareActivity.class)
                        .putExtra("code", mTvMyCode.getText().toString()));
                break;
            case R.id.tv_vip_course:
                if (OENType.currentType() == OENType.yindian) {
                    startActivity(new Intent(getActivity(), WebViewActivity.class)
                            .putExtra("title", "VIP教程")
                            .putExtra("url", "http://mp.weixin.qq.com/s/SuvG3G3lW7JC8RjFUT9MVw"));
                } else {
                    ToastUtils.show(getActivity(), "正在升级中...");
                }

                break;
        }
    }

}
