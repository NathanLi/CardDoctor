package com.yunkahui.datacubeper.upgradeJoin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

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

public class UpgradeJoinIntroduceActivity extends AppCompatActivity implements IActivityStatusBar {

    public static final int TYPE_VIP = 1;
    public static final int TYPE_AGENT = 2;
    public static final int TYPE_OEM = 3;

    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mType = getIntent().getIntExtra("type", TYPE_VIP);
        switch (mType) {
            case TYPE_VIP:
                setContentView(R.layout.activity_upgrade_join_introduce);
                setTitle("VIP会员");
                break;
            case TYPE_AGENT:
                setContentView(R.layout.activity_upgrade_join_agent_introduce);
                setTitle("代理商");
                break;
            case TYPE_OEM:
                setContentView(R.layout.activity_upgrade_join_oem_introduce);
                setTitle("独立品牌OEM定制");
                break;
        }
        super.onCreate(savedInstanceState);

    }

    /**
     * 查询用户是否已申请代理商或OEM
     */
    private void applyAgent(final boolean isOem) {
        LoadingViewDialog.getInstance().show(this);
        new UpgradeJoinLogic().loadAgentIsApply(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询是否申请代理->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        Intent intent = new Intent(UpgradeJoinIntroduceActivity.this, AgentApplyActivity.class);
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
    public void initView() {
        switch (mType) {
            case TYPE_VIP:
                String text5 = "直接推荐朋友升级VIP，享受<font color='#0085ff'>100元</font>+<font color='#0085ff'>万分之10</font>奖励；" +
                        "\n间接推荐朋友升级VIP，享受<font color='#0085ff'>50元</font>+<font color='#0085ff'>万分之5</font>奖励。";
                ((TextView) findViewById(R.id.textView5)).setText(Html.fromHtml(text5));
                break;
            case TYPE_AGENT:

                break;
            case TYPE_OEM:
                String text3 = "2、<font color='#0085ff'>专业化的代理商管理系统</font>，可自定义结算政策、分润政策、代理政策、VIP升级政策、会员裂变政策、代理商特殊奖励政策等营销推广政策；";
                ((TextView) findViewById(R.id.textView3)).setText(Html.fromHtml(text3));
                String text4 = "3、<font color='#0085ff'>总部有专业技术研发团队、售后服务团队的全方位支持</font>；";
                ((TextView) findViewById(R.id.textView4)).setText(Html.fromHtml(text4));
                break;
        }
        if (TYPE_VIP != mType) {
            findViewById(R.id.show_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (mType) {
                        case TYPE_AGENT:
                            applyAgent(false);
                            break;
                        case TYPE_OEM:
                            applyAgent(true);
                            break;
                    }
                }
            });
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
