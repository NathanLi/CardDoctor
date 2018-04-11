package com.yunkahui.datacubeper.upgradeJoin.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBeanList;
import com.yunkahui.datacubeper.common.bean.VipPackage;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.upgradeJoin.logic.UpgradeVipLogic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * VIP会员套餐
 */
public class UpgradeVipActivity extends AppCompatActivity implements IActivityStatusBar,View.OnClickListener, UpgradeJoinItemView.OnChildClickListener {

    private final int RESULT_CODE_VIP1=1001;
    private final int RESULT_CODE_VIP2=1002;
    private final int RESULT_CODE_VIP3=1003;

    private UpgradeJoinItemView mUpgradeJoinItemView1;
    private UpgradeJoinItemView mUpgradeJoinItemView2;
    private UpgradeJoinItemView mUpgradeJoinItemView3;

    private List<VipPackage> mVipPackages=new ArrayList<>();
    private UpgradeVipLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_upgrade_vip);
        super.onCreate(savedInstanceState);
        setTitle("VIP会员套餐");
        mUpgradeJoinItemView1=findViewById(R.id.upgrade_join_item_view1);
        mUpgradeJoinItemView2=findViewById(R.id.upgrade_join_item_view2);
        mUpgradeJoinItemView3=findViewById(R.id.upgrade_join_item_view3);

        mUpgradeJoinItemView1.setOnClickListener(this);
        mUpgradeJoinItemView2.setOnClickListener(this);
        mUpgradeJoinItemView3.setOnClickListener(this);
        mUpgradeJoinItemView1.setOnChildClickListener(this);
        mUpgradeJoinItemView2.setOnChildClickListener(this);
        mUpgradeJoinItemView3.setOnChildClickListener(this);

        mLogic=new UpgradeVipLogic();

        initActionBar();
        loadData();
    }

    private void initActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.setTitle("VIP会员套餐");
    }


    private void loadData(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadData(this, new SimpleCallBack<BaseBeanList<VipPackage>>() {
            @Override
            public void onSuccess(BaseBeanList<VipPackage> vipPackageBaseBeanList) {
                LoadingViewDialog.getInstance().dismiss();

                LogUtils.e("Vip会员套餐->"+vipPackageBaseBeanList.toString());

            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
            }
        });
    }


    private void loadVipData(int type){
        switch (type){
            case RESULT_CODE_VIP1:
//                requestOpenVip1("menu1_step1",mVipPackages.get(0).getMenuPrice());
                break;
            case RESULT_CODE_VIP2:
                showPayDialog("开通套餐二支付\n¥"+mVipPackages.get(1).getPrice(),RESULT_CODE_VIP2);
                break;
            case RESULT_CODE_VIP3:
                showPayDialog("开通套餐二支付\n¥"+mVipPackages.get(2).getPrice(),RESULT_CODE_VIP3);
                break;
        }
    }

//    private void requestOpenVip1(final String menuType, final String money){
//        RemindUtil.remindHUD(this);
//        Map<String,String> param=new HashMap<>();
//        param.put("menuType",menuType);
//        param.put("paymentCode","alipay");
//        RequestHelper helper = new RequestHelper( new SpecialConverterFactory(SpecialConverterFactory.ConverterType.Converter_Single));
//        helper.getRequestApi().requestOpenVip(param).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TopBean>() {
//            @Override
//            public void onCompleted() {
//
//            }
//            @Override
//            public void onError(Throwable e) {
//                RemindUtil.dismiss();
//                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNext(TopBean topBean) {
//                RemindUtil.dismiss();
//                Log.e("2018","VIP1="+topBean.getResponse().toString());
//                if(topBean.getCode()==RequestHelper.success){
//                    String mOrderInfo="";
//                    switch (menuType){
//                        case "menu1_step1":
//                            requestOpenVip1("menu1_step2",money);
//                            break;
//                        case "menu1_step2":
//                            mOrderInfo=topBean.getResponse().optJSONObject("respData").optString("order_info");
//                            Intent intent1=new Intent(UpgradeVipActivity.this,MemUpgradeActivity.class);
//                            intent1.putExtra("money",money);
//                            intent1.putExtra("orderinfo",mOrderInfo);
//                            startActivityForResult(intent1,RESULT_CODE_VIP1);
//                            break;
//                        case "menu2":
//                            mOrderInfo=topBean.getResponse().optJSONObject("respData").optString("order_info");
////                            Intent intent2=new Intent(UpgradeVipActivity.this,MemUpgradeActivity.class);
////                            intent2.putExtra("money",money);
////                            intent2.putExtra("orderinfo",mOrderInfo);
////                            startActivityForResult(intent2,RESULT_CODE_VIP2);
//                            payEvent(RESULT_CODE_VIP2,mOrderInfo);
//                            break;
//                        case "menu3":
//                            mOrderInfo=topBean.getResponse().optJSONObject("respData").optString("order_info");
////                            Intent intent3=new Intent(UpgradeVipActivity.this,MemUpgradeActivity.class);
////                            intent3.putExtra("money",money);
////                            intent3.putExtra("orderinfo",mOrderInfo);
////                            startActivityForResult(intent3,RESULT_CODE_VIP3);
//                            payEvent(RESULT_CODE_VIP3,mOrderInfo);
//                            break;
//                    }
//                }else{
//                    showDialog(topBean.getMessage(),false);
//                }
//            }
//        });
//    }

    private void showDialog(String text,boolean isCancel){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this)
                .setMessage(text);
        if(!isCancel){
            dialog.setPositiveButton("关闭",null);
        }else{
            dialog.setPositiveButton("前往", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    PosPayActivity.actionStart(UpgradeVipActivity.this);
                }
            }).setNeutralButton("取消",null);
        }
        dialog.create().show();
    }


    private void showPayDialog(String text, final int type){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this)
                .setMessage(text);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (type){
                        case RESULT_CODE_VIP2:
//                            requestOpenVip1("menu2",mVipPackages.get(1).getMenuPrice());
                            break;
                        case RESULT_CODE_VIP3:
//                            requestOpenVip1("menu3",mVipPackages.get(2).getMenuPrice());
                            break;
                    }

                }
            }).setNeutralButton("取消",null);
        dialog.create().show();
    }


//    private void payEvent(final int type, String orderInfo){
//        PayUtil payUtil = new PayUtil();
//        payUtil.payEvent(this, orderInfo, new DealInterface<PayUtil>() {
//
//            @Override
//            public void success(PayUtil object) {
//                switch (type){
//                    case RESULT_CODE_VIP2:
//                        showDialog("套餐二：“落地POS”功能开通成功，可前往申请POS",true);
//                        break;
//                    case RESULT_CODE_VIP3:
//                        showDialog("套餐三：“自动交易+落地POS”功能开通成功，可前往申请POS",true);
//                        break;
//                }
//                loadData();
//            }
//
//            @Override
//            public void failure(String error) {
//                Toast.makeText(BaseApplication.getContext(), error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case RESULT_CODE_VIP1:
                    break;
                case RESULT_CODE_VIP2:
                    showDialog("套餐二：“落地POS”功能开通成功，可前往申请POS",true);
                    break;
                case RESULT_CODE_VIP3:
                    showDialog("套餐三：“自动交易+落地POS”功能开通成功，可前往申请POS",true);
                    break;
            }
            loadData();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upgrade_join_item_view1:
                Intent intent1=new Intent(this,VipInstructionActivity.class);
                intent1.putExtra("text",mVipPackages.get(0).getDesc());
                startActivity(intent1);
                break;
            case R.id.upgrade_join_item_view2:
                Intent intent2=new Intent(this,VipInstructionActivity.class);
                intent2.putExtra("text",mVipPackages.get(1).getDesc());
                startActivity(intent2);
                break;
            case R.id.upgrade_join_item_view3:
                Intent intent3=new Intent(this,VipInstructionActivity.class);
                intent3.putExtra("text",mVipPackages.get(2).getDesc());
                startActivity(intent3);
                break;
        }
    }


    @Override
    public void onChildClick(View parent, View view) {
        switch (parent.getId()){
            case R.id.upgrade_join_item_view1:
                loadVipData(RESULT_CODE_VIP1);
                break;
            case R.id.upgrade_join_item_view2:
                loadVipData(RESULT_CODE_VIP2);
                break;
            case R.id.upgrade_join_item_view3:
                if(!"01".equals(mVipPackages.get(0).getOpenState())){
                    loadVipData(RESULT_CODE_VIP3);
                }
                break;
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
