package com.yunkahui.datacubeper.upgradeJoin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.upgradeJoin.logic.UpgradeJoinLogic;

import org.json.JSONObject;

//升级加盟
public class UpgradeJoinActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener, UpgradeJoinItemView.OnChildClickListener {

    private UpgradeJoinItemView mUpgradeJoinItemView1;
    private UpgradeJoinItemView mUpgradeJoinItemView2;
    private UpgradeJoinItemView mUpgradeJoinItemView3;

    private UpgradeJoinLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_upgrade_join);
        super.onCreate(savedInstanceState);
        setTitle("升级加盟");
    }

    @Override
    public void initView() {
        mUpgradeJoinItemView1 = findViewById(R.id.upgrade_join_item_view1);
        mUpgradeJoinItemView2 = findViewById(R.id.upgrade_join_item_view2);
        mUpgradeJoinItemView3 = findViewById(R.id.upgrade_join_item_view3);

        mUpgradeJoinItemView1.setOnClickListener(this);
        mUpgradeJoinItemView2.setOnClickListener(this);
        mUpgradeJoinItemView3.setOnClickListener(this);
        mUpgradeJoinItemView1.setOnChildClickListener(this);
        mUpgradeJoinItemView2.setOnChildClickListener(this);
        mUpgradeJoinItemView3.setOnChildClickListener(this);
    }

    @Override
    public void initData() {
        mLogic = new UpgradeJoinLogic();
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, UpgradeJoinIntroduceActivity.class);
        switch (v.getId()) {
            case R.id.upgrade_join_item_view1:
                intent.putExtra("type", UpgradeJoinIntroduceActivity.TYPE_VIP);
                break;
            case R.id.upgrade_join_item_view2:
                intent.putExtra("type", UpgradeJoinIntroduceActivity.TYPE_AGENT);
                break;
            case R.id.upgrade_join_item_view3:
                intent.putExtra("type", UpgradeJoinIntroduceActivity.TYPE_OEM);
                break;
        }
        startActivity(intent);
    }

    /**
     * 查询用户是否已申请代理商或OEM
     */
    private void applyAgent(final boolean isOem) {
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadAgentIsApply(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询是否申请代理->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        Intent intent = new Intent(UpgradeJoinActivity.this, AgentApplyActivity.class);
                        intent.putExtra("type", isOem ? AgentApplyActivity.TYPE_OEM : AgentApplyActivity.TYPE_AGENT);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询是否申请代理失败->" + throwable.toString());
                ToastUtils.show(getApplicationContext(), "请求失败");
            }
        });
    }

    @Override
    public void onChildClick(View parent, View view) {
        switch (parent.getId()) {
            case R.id.upgrade_join_item_view1:
                startActivity(new Intent(this, UpgradeVipActivity.class));
                break;
            case R.id.upgrade_join_item_view2:
                applyAgent(false);
                break;
            case R.id.upgrade_join_item_view3:
                applyAgent(true);
                break;
        }
    }
}
