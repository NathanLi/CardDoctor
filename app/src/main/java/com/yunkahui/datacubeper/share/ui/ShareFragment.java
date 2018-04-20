package com.yunkahui.datacubeper.share.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.view.DoubleBlockView;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.ui.QrShareActivity;
import com.yunkahui.datacubeper.share.logic.ShareLogic;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class ShareFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ShareFragment";
    private DoubleBlockView mDoubleBlockView1;
    private DoubleBlockView mDoubleBlockView2;
    private ShareLogic mShareLogic;
    private TextView mTvRestCode;
    private TextView mTvMyCode;

    @Override
    public void initData() {
        mShareLogic = new ShareLogic();
        initListener();
        mShareLogic.requestSharePageInfo(mActivity, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    JSONObject respData = object.optJSONObject("respData");
                    if (respData != null) {
                        mDoubleBlockView1.setLeftNum(respData.optString("userCommissions"))
                                .setRightNum(respData.optString("userFenruns"));
                        mDoubleBlockView2.setLeftNum(String.valueOf(respData.optInt("commonMemberCount")))
                                .setRightNum(String.valueOf(respData.optInt("vipMemberCount")));
                        mTvRestCode.setText(String.valueOf(respData.optInt("reNum")));
                        mTvMyCode.setText(respData.optString("userUniqueCode"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void initListener() {
        mDoubleBlockView1.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, WalletActivity.class)
                .putExtra("from", "share"));
            }
        }).setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ShareProfitActivity.class));
            }
        });
        mDoubleBlockView2.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MemberActivity.class));
            }
        }).setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MemberActivity.class));
            }
        });
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_share));
        mDoubleBlockView1 = view.findViewById(R.id.double_block_view_1);
        mDoubleBlockView2 = view.findViewById(R.id.double_block_view_2);
        mTvRestCode = view.findViewById(R.id.tv_rest_code);
        mTvMyCode = view.findViewById(R.id.tv_my_code);
        view.findViewById(R.id.btn_produce_code).setOnClickListener(this);
        view.findViewById(R.id.tv_link_share).setOnClickListener(this);
        view.findViewById(R.id.tv_qr_share).setOnClickListener(this);
        view.findViewById(R.id.tv_vip_course).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_produce_code:
                mShareLogic.createActivationCode(mActivity, new SimpleCallBack<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        try {
                            JSONObject object = new JSONObject(jsonObject.toString());
                            if ("0074".equals(object.getString("respCode"))) {
                                Toast.makeText(mActivity, object.getString("respDesc"), Toast.LENGTH_SHORT).show();
                            }
                            Log.e(TAG, "onSuccess: " + jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
                break;
            case R.id.tv_link_share:
                startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "http://www.yunkahui.cn/zbl_web/index.html?user_unique_code=" + mTvMyCode.getText().toString().trim()));
                break;
            case R.id.tv_qr_share:
                startActivity(new Intent(mActivity, QrShareActivity.class).putExtra("code", mTvMyCode.getText().toString()));
                break;
            case R.id.tv_vip_course:
                startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "http://mp.weixin.qq.com/s/SuvG3G3lW7JC8RjFUT9MVw"));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share;
    }
}