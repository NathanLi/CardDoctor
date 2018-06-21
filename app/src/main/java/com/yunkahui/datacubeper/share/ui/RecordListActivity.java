package com.yunkahui.datacubeper.share.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.PlanSpinner;
import com.yunkahui.datacubeper.home.ui.WithdrawForCardActivity;
import com.yunkahui.datacubeper.mine.ui.BindZFBActivity;
import com.yunkahui.datacubeper.share.logic.RecordType;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 明细列表  （新）
 */
public class RecordListActivity extends AppCompatActivity implements IActivityStatusBar {

    private PlanSpinner mPlanSpinnerRecordType;
    private PlanSpinner mPlanSpinnerRecordTime;
    private RecordListNewFragment mRecordListNewFragment;
    private RecordListNew2Fragment mRecordListNew2Fragment;

    private RecordType mRecordType;

    private List<RecordType> mTypes;

    private long mStartTime;
    private long mEndTime;

    private ArrayList<CardSelectorBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_record_list);
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        setTitle(TextUtils.isEmpty(title) ? "明细" : title);
    }

    @Override
    public void initData() {
        mRecordType = (RecordType) getIntent().getSerializableExtra("type");
        initTypes();

        mList = new ArrayList<>();
        List<String> types = new ArrayList<>();
        if (mRecordType == RecordType.balance_all) {
            types.add("全部");
            types.add("充值");
            types.add("提现");
        } else {
            types.add("收入");
            types.add("提现");
        }

        List<String> times = new ArrayList<>();
        times.add("近3个月");
        times.add("近半年");
        times.add("今年");
        times.add("更早");

        mPlanSpinnerRecordType.setList(types);
        mPlanSpinnerRecordTime.setList(times);
        mRecordListNewFragment = new RecordListNewFragment();
        mRecordListNew2Fragment = new RecordListNew2Fragment();

        mStartTime = TimeUtils.getCalendarCompluteForMonth(-3);
        mEndTime = System.currentTimeMillis();

        selectPage();

        mPlanSpinnerRecordTime.setOnItemSelectListener(new PlanSpinner.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, String text) {
                switch (position) {
                    case 0:
                        mStartTime = TimeUtils.getCalendarCompluteForMonth(-3);
                        mEndTime = System.currentTimeMillis();
                        break;
                    case 1:
                        mStartTime = TimeUtils.getCalendarCompluteForMonth(-6);
                        mEndTime = System.currentTimeMillis();
                        break;
                    case 2:
                        mStartTime = TimeUtils.getThisYear();
                        mEndTime = System.currentTimeMillis();
                        break;
                    case 3:
                        mStartTime = 0;
                        mEndTime = 0;
                        break;
                }
                if (mRecordListNewFragment.isVisible()) {
                    mRecordListNewFragment.update();
                }
                if (mRecordListNew2Fragment.isVisible()) {
                    mRecordListNew2Fragment.update();
                }

            }
        });
        mPlanSpinnerRecordType.setOnItemSelectListener(new PlanSpinner.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, String text) {
                mRecordType = mTypes.get(position);
                selectPage();
                if (mRecordListNewFragment.isVisible()) {
                    mRecordListNewFragment.update();
                }
                if (mRecordListNew2Fragment.isVisible()) {
                    mRecordListNew2Fragment.update();
                }
            }
        });

    }

    public void initTypes() {
        mTypes = new ArrayList<>();
        switch (mRecordType) {
            case MyWallet_come:
                mTypes.add(RecordType.MyWallet_come);
                mTypes.add(RecordType.MyWallet_withdraw);
                break;
            case online_come:
                mTypes.add(RecordType.online_come);
                mTypes.add(RecordType.online_withdraw);
                break;
            case pos_come:
                mTypes.add(RecordType.pos_come);
                mTypes.add(RecordType.pos_withdraw);
                break;
            case balance_all:
                mTypes.add(RecordType.balance_all);
                mTypes.add(RecordType.balance_come);
                mTypes.add(RecordType.balance_withdraw);
                break;
        }
    }


    @Override
    public void initView() {
        mPlanSpinnerRecordType = findViewById(R.id.plan_spinner_record_type);
        mPlanSpinnerRecordTime = findViewById(R.id.plan_spinner_record_time);
    }

    //选择列表页面(分组或不分组)
    public void selectPage() {
        switch (mRecordType) {
            case MyWallet_come:
            case online_come:
            case pos_come:
            case balance_all:
                LogUtils.e("替换页面1");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mRecordListNewFragment).commitNowAllowingStateLoss();
                break;
            case MyWallet_withdraw:
            case online_withdraw:
            case pos_withdraw:
            case balance_come:
            case balance_withdraw:
                LogUtils.e("替换页面2");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mRecordListNew2Fragment).commitNowAllowingStateLoss();
                break;
        }
    }

    public long getStartTime() {
        return mStartTime;
    }

    public long getEndTime() {
        return mEndTime;
    }


    public RecordType getRecordType() {
        return mRecordType;
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        switch (mRecordType) {
            case MyWallet_come:
            case online_come:
                menu.add(1, 1, 1, "提现").setIcon(R.mipmap.ic_withdraw_text).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                switch (mRecordType) {
                    case MyWallet_come:
                        checkUserZFB();
                        break;
                    case online_come:
                        queryCreditCardList();
                        break;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //******** 获取储蓄卡 ********

    public void checkCashCard(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkCashCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //******** 查询支付宝账户 ********
    public void checkUserZFB(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkUserBindZFB(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //******** 获取储蓄卡 ********
    private void queryCreditCardList() {
        LoadingViewDialog.getInstance().show(this);
        checkCashCard(this, new SimpleCallBack<BaseBean>() {
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
                    startActivity(new Intent(RecordListActivity.this, WithdrawForCardActivity.class)
                            .putExtra("title", "分润提现")
                            .putExtra("withdrawType", "01")
                            .putExtra("list", mList));
                } else {
                    Toast.makeText(RecordListActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "获取储蓄卡失败 " + throwable.toString());
            }
        });
    }

    //******** 查询支付宝信息 ********
    private void checkUserZFB() {
        checkUserZFB(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("支付宝信息->" + baseBean.getJsonObject().toString());
                try {
                    if (RequestUtils.SUCCESS.equals(baseBean.getJsonObject().optString("respCode"))) {
                        startActivity(new Intent(RecordListActivity.this, WithdrawForZFBActivity.class)
                                .putExtra("withdrawType", "00")
                                .putExtra("json", baseBean.getJsonObject().toString()));
                    } else {
                        showBindZFBDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(RecordListActivity.this, "获取支付宝信息失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBindZFBDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("尚未绑定支付宝，请前往绑定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(RecordListActivity.this, BindZFBActivity.class));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
