package com.yunkahui.datacubeper.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.CustomViewPager;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.logic.HomeProfitLogic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class HomeProfitActivity extends AppCompatActivity implements IActivityStatusBar {

    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;

    private HomeProfitLogic mLogic;
    private ArrayList<CardSelectorBean> mList;

    @Override
    public void initData() {
        mLogic = new HomeProfitLogic();
        mList = new ArrayList<>();

        String[] tabTitles = {"分润收入", "分润提现"};
        List<Fragment> fragments = new ArrayList<>();

        ProfitIncomeFragment profitIncomeFragment = new ProfitIncomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", ProfitIncomeFragment.TYPE_FUN_RUN);
        profitIncomeFragment.setArguments(bundle);

        fragments.add(profitIncomeFragment);
        fragments.add(new ProfitWithdrawFragment());
        for (String title : tabTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
        }
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, tabTitles);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setScanScroll(true);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "提现").setIcon(R.mipmap.ic_withdraw_text).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                queryCreditCardList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //******** 获取储蓄卡 ********
    private void queryCreditCardList() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkCashCard(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("储蓄卡->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                CardSelectorBean bean;
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    JSONObject json = object.optJSONObject("respData");
                    bean = new CardSelectorBean();
                    bean.setCardId(json.optInt("Id"));
                    bean.setBankCardName(json.optString("bankcard_name"));
                    bean.setBankCardNum(json.optString("bankcard_num"));
                    bean.setBankCardTel(json.optString("bankcard_tel"));
                    bean.setCardHolder(json.optString("cardholder"));
                    bean.setChecked(false);
                    mList.clear();
                    mList.add(bean);
                    mList.get(0).setChecked(true);
                    startActivity(new Intent(HomeProfitActivity.this, WithdrawForCardActivity.class)
                            .putExtra("title", "分润提现")
                            .putExtra("withdrawType", "01")
                            .putExtra("list", mList));
                } else {
                    Toast.makeText(HomeProfitActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "获取储蓄卡失败 " + throwable.toString());
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_profit);
        super.onCreate(savedInstanceState);
        setTitle("累计分润");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
