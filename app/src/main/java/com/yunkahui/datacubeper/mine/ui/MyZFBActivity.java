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
import android.widget.LinearLayout;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.mine.logic.MyZFBLogic;

import org.json.JSONObject;

//我的支付宝
public class MyZFBActivity extends AppCompatActivity implements IActivityStatusBar {

    private final int RESULT_CODE=1001;

    private SimpleEditTextView mEditTextViewZFB;
    private SimpleEditTextView mEditTextViewName;
    private LinearLayout mLinearLayoutZFB;
    private ImageView mImageViewNoData;

    private MenuItem mMenuItemAdd;
    private MenuItem mMenuItemUnBind;
    private MyZFBLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_zfb);
        super.onCreate(savedInstanceState);
        setTitle("我的支付宝");

    }

    @Override
    public void initData() {
        mLogic = new MyZFBLogic();
        loadData();
    }

    @Override
    public void initView() {
        mEditTextViewZFB=findViewById(R.id.simple_input_item_zfb);
        mEditTextViewName=findViewById(R.id.simple_input_item_name);
        mLinearLayoutZFB=findViewById(R.id.linear_layout_zfb);
        mImageViewNoData=findViewById(R.id.iv_no_data);

        mEditTextViewZFB.setEnabled(false);
        mEditTextViewName.setEnabled(false);
        mEditTextViewZFB.getEditTextInput().setTextColor(getResources().getColor(R.color.black));
        mEditTextViewName.getEditTextInput().setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    private void loadData() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkUserZFB(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("支付宝信息->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        mMenuItemUnBind.setVisible(true);
                        mMenuItemAdd.setVisible(false);
                        mImageViewNoData.setVisibility(View.GONE);
                        mLinearLayoutZFB.setVisibility(View.VISIBLE);
                        mEditTextViewZFB.setText(object.optJSONObject("respData").optString("alipay_account"));
                        mEditTextViewName.setText(object.optJSONObject("respData").optString("ail_true_name"));
                    } else {
                        mMenuItemAdd.setVisible(true);
                        mMenuItemUnBind.setVisible(false);
                        mImageViewNoData.setVisibility(View.VISIBLE);
                        mLinearLayoutZFB.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("支付宝信息失败->" + throwable.toString());
            }
        });
    }

    //解绑支付宝账号
    private void unBindZFB() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.unBindZFB(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("解绑支付宝->"+baseBean.getJsonObject().toString());
                try {
                    JSONObject object=baseBean.getJsonObject();
                    ToastUtils.show(getApplicationContext(),object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        loadData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"请求失败 "+throwable.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==RESULT_CODE){
            loadData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivityForResult(new Intent(this, BindZFBActivity.class),RESULT_CODE);
                break;
            case 2:
                showUnBindDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //解绑弹窗
    private void showUnBindDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("是否解绑当前支付宝?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unBindZFB();
                    }
                })
                .setNeutralButton("取消", null)
                .create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenuItemAdd = menu.add(1, 1, 1, "添加").setIcon(R.mipmap.ic_icon_add).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mMenuItemUnBind = menu.add(1, 2, 2, "解绑").setIcon(R.mipmap.ic_icon_unbind).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mMenuItemAdd.setVisible(false);
        mMenuItemUnBind.setVisible(false);
        return true;
    }

}
