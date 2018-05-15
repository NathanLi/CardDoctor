package com.yunkahui.datacubeper.home.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.ui.ApplyPosActivity;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.DoubleBlockView;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.adapter.HomeItemAdapter;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.logic.HomeLogic;
import com.yunkahui.datacubeper.home.other.NotScrollGridLayoutManager;
import com.yunkahui.datacubeper.mine.logic.MineLogic;
import com.yunkahui.datacubeper.mine.ui.RealNameAuthActivity;
import com.yunkahui.datacubeper.share.ui.WebViewActivity;
import com.yunkahui.datacubeper.upgradeJoin.ui.UpgradeJoinActivity;

import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private DoubleBlockView mDoubleBlockView;
    private RecyclerView mRecyclerView;

    private HomeLogic mLogic;
    private String mUserBalance;

    @Override
    public void initData() {
        mLogic = new HomeLogic();
        mDoubleBlockView.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("0".equals(DataUtils.getInfo().getVIP_status())){
                    ToastUtils.show(getActivity(),"请先升级VIP");
                }else{
                    startActivity(new Intent(mActivity, HomeWalletActivity.class).putExtra("money", mUserBalance));
                }
            }
        }).setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("0".equals(DataUtils.getInfo().getVIP_status())){
                    ToastUtils.show(getActivity(),"请先升级VIP");
                }else{
                    startActivity(new Intent(mActivity, HomeProfitActivity.class));
                }
            }
        });

        final List<HomeItem> homeItems = mLogic.parsingJSONForHomeItem(getActivity());
        HomeItemAdapter homeItemAdapter = new HomeItemAdapter(R.layout.layout_list_item_home, homeItems);
        homeItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (homeItems.get(position).getId()) {
                    case 101:
                        startActivity(new Intent(mActivity, TodayOperationActivity.class));
                        break;
                    case 102:
                        checkRealNameAuthStatus();
                        break;
                    case 103:
                        startActivity(new Intent(mActivity, UpgradeJoinActivity.class));
                        break;
                    case 104:
                        ApplyPosActivity.startAction(getActivity());
                        break;
                    case 105:
                        startActivity(new Intent(mActivity, WebViewActivity.class)
                                .putExtra("title", "个人征信")
                                .putExtra("url", "https://ipcrs.pbccrc.org.cn/"));
                        break;
                    case 106:
                        startActivity(new Intent(mActivity, WebViewActivity.class)
                                .putExtra("title", "信贷黑名单")
                                .putExtra("url", "http://www.025hmd.com"));
                        break;
                    case 107:
                        startActivity(new Intent(mActivity, WebViewActivity.class)
                                .putExtra("title", "失信黑名单")
                                .putExtra("url", "http://shixin.court.gov.cn/"));
                        break;
                    case 108:
                        startActivity(new Intent(mActivity, WebViewActivity.class)
                                .putExtra("title", "违章查询")
                                .putExtra("url", "http://m.46644.com/illegal/?tpltype=weixin"));
                        break;
                    case 109:
                        startActivity(new Intent(mActivity, WebViewActivity.class)
                                .putExtra("title", "一键办卡")
                                .putExtra("url", "http://kadai.yunkahui.cn/touch/index.php?uid=64"));
                        break;
                    case 110:
                        startActivity(new Intent(mActivity, WebViewActivity.class)
                                .putExtra("title", "贷款专区")
                                .putExtra("url", "http://kadai.yunkahui.cn/touch/index.php?p=products_list&lanmu=18&from=timeline&isappinstalled=0"));
                        break;
                    case 111:
                        startActivity(new Intent(mActivity, BanksActivity.class));
                        break;
                    case 112:
                        startActivity(new Intent(mActivity, WebViewActivity.class)
                                .putExtra("title", "保险服务")
                                .putExtra("url", "http://www.epicc.com.cn/wap/views/proposal/giveactivity/JBD_S/?productcode=JBD_S&plantype=B?cmpid=2017pcluodiye"));
                        break;
                }

            }
        });
        homeItemAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new NotScrollGridLayoutManager(mActivity, 4));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                outRect.right = 3;
                outRect.bottom = 3;
            }
        });
        mRecyclerView.setAdapter(homeItemAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserFinance();
    }

    //******** 获取余额、分润 ********
    private void initUserFinance() {
        LoadingViewDialog.getInstance().show(mActivity);
        mLogic.loadUserFinance(mActivity, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("余额分润->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    JSONObject object = baseBean.getJsonObject();
                    JSONObject respData = object.optJSONObject("respData");
                    mUserBalance = respData.optString("user_balance");
                    mDoubleBlockView.setLeftValue(mUserBalance).setRightValue(respData.optString("user_fenruns"));
                } else {
                    Toast.makeText(mActivity, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(mActivity, "获取余额分润失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //查询实名认证状态
    private void checkRealNameAuthStatus() {
        LoadingViewDialog.getInstance().show(mActivity);
        new MineLogic().checkRealNameAuthStatus(mActivity, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询实名认证状态->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    switch (object.optJSONObject("respData").optString("status")) {
                        case "0":
                            startActivity(new Intent(mActivity, RealNameAuthActivity.class));
                            break;
                        case "1":
                            ToastUtils.show(mActivity, "已实名认证");
                            break;
                        case "2":
                            ToastUtils.show(mActivity, "正在审核认证中");
                            break;
                        case "3":
                            ToastUtils.show(mActivity, "审核认证不成功，请重新认证");
                            startActivity(new Intent(mActivity, RealNameAuthActivity.class));
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询实名认证状态失败->" + throwable.toString());
            }
        });
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.home));
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mDoubleBlockView = view.findViewById(R.id.double_block_view);

        view.findViewById(R.id.rl_scan).setOnClickListener(this);
        view.findViewById(R.id.rl_qr).setOnClickListener(this);
        view.findViewById(R.id.rl_receive_money).setOnClickListener(this);
        view.findViewById(R.id.text_view_qr_code).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_scan:
                startActivity(new Intent(mActivity, ScanActivity.class));
                break;
            case R.id.rl_qr:
                break;
            case R.id.rl_receive_money:
                break;
            case R.id.text_view_qr_code:
                if (TextUtils.isEmpty(DataUtils.getInfo().getUser_qrcode_img())) {
                    ToastUtils.show(mActivity, "数据加载中...");
                } else {
                    startActivity(new Intent(mActivity, QrShareActivity.class)
                            .putExtra("code", DataUtils.getInfo().getUser_qrcode_img()));
                }
                break;
        }
    }
}
