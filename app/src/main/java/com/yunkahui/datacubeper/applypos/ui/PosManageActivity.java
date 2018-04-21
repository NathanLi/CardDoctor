package com.yunkahui.datacubeper.applypos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.PosManageLogic;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

//POS管理
public class PosManageActivity extends AppCompatActivity implements IActivityStatusBar{

    private PosManageLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_manager);
        super.onCreate(savedInstanceState);
        setTitle("POS管理");
    }

    @Override
    public void initData() {
        mLogic=new PosManageLogic();
        loadData();
    }

    @Override
    public void initView() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    public void loadData(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadPosManageData(this, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("POS管理->"+jsonObject.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();

            }
        });
    }


}
