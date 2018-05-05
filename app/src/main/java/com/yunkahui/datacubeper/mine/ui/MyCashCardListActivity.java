package com.yunkahui.datacubeper.mine.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BankCard;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.mine.logic.MyCashCardListLogic;

import org.json.JSONException;
import org.json.JSONObject;

//我的储蓄卡
public class MyCashCardListActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    public final int RESULT_CODE_UPDATE = 1001;

    private ImageView mImageViewNoData;
    private CashCardItemView mCashCardItemView;

    private MenuItem mMenuItemAdd;
    private MyCashCardListLogic mLogic;
    private BankCard mBankCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_cash_card_list);
        super.onCreate(savedInstanceState);
        setTitle("我的储蓄卡");
    }

    @Override
    public void initData() {
        mLogic = new MyCashCardListLogic();
        loadData();
    }

    @Override
    public void initView() {
        mImageViewNoData = findViewById(R.id.iv_no_data);
        mCashCardItemView = findViewById(R.id.cash_card_item_view);
        mCashCardItemView.setOnClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadData() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkCashCard(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                try {
                    LoadingViewDialog.getInstance().dismiss();
                    JSONObject object = baseBean.getJsonObject();
                    LogUtils.e("我的儲蓄卡->" + baseBean.getJsonObject().toString());
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        JSONObject json = object.optJSONObject("respData");
                        mBankCard = new BankCard();
                        mBankCard.setId(json.optInt("Id"));
                        mBankCard.setBankcard_name(json.optString("bankcard_name"));
                        mBankCard.setBankcard_num(json.optString("bankcard_num"));
                        mBankCard.setBankcard_tel(json.optString("bankcard_tel"));
                        mBankCard.setCardholder(json.optString("cardholder"));

                        mCashCardItemView.setData(mBankCard);
                        mCashCardItemView.setVisibility(View.VISIBLE);
                        mImageViewNoData.setVisibility(View.GONE);
                        mMenuItemAdd.setVisible(false);
                    } else {
                        ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                        mCashCardItemView.setVisibility(View.GONE);
                        mImageViewNoData.setVisibility(View.VISIBLE);
                        mMenuItemAdd.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });

    }

    private void deleteCashCard() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.deleteCashCard(this, mBankCard.getId(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("删除储蓄卡->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        loadData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_CODE_UPDATE) {
            loadData();
        }
    }

    private void showDeleteCashCardDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("是否解除绑定该卡片？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCashCard();
                    }
                })
                .setNeutralButton("取消", null)
                .create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenuItemAdd = menu.add(1, 1, 1, "添加").setIcon(R.mipmap.ic_icon_add).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mMenuItemAdd.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(this, AddCashCardActivity.class);
                startActivityForResult(intent, RESULT_CODE_UPDATE);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cash_card_item_view:
                showDeleteCashCardDialog();
                break;
        }
    }
}
