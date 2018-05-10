package com.yunkahui.datacubeper.applypos.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.logic.HomeLogic;
import com.yunkahui.datacubeper.upgradeJoin.ui.UpgradeVipActivity;

import org.json.JSONObject;

//申请POS
public class ApplyPosActivity extends AppCompatActivity implements IActivityStatusBar {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_apply_pos);
        super.onCreate(savedInstanceState);
        setTitle("申请POS");
    }

    /**
     * 跳转前置
     */
    public static void startAction(Activity activity) {
        checkApplyPosStatus(activity);
    }

    //查询POS开通状态
    private static void checkApplyPosStatus(final Activity activity) {
        LoadingViewDialog.getInstance().show(activity);
        new HomeLogic().checkPosApplyStatus(activity, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("POS状态->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    JSONObject json = object.optJSONObject("respData");
                    DataUtils.getInfo().setTruename(json.optString("truename"));
                    if (!"1".equals(json.optString("identify_status"))) {
                        ToastUtils.show(activity, "请先实名认证");
                    } else if (!"1".equals(json.optString("VIP_status"))) {
                        ToastUtils.show(activity, "请先升级VIP");
                    } else {
                        switch (json.optString("tua_status")) {
                            case "-1":  //落地POS没开通
                                showDialog(activity);
                                break;
                            case "0":   //已付款
                            case "1":   //审核中
                            case "2":   //审核通过
                            case "3":   //审核不通过
                            case "4":   //审核关闭
                            case "5":   //已寄出
                            case "9":   //手持POS照片提交成功
                            case "10":  //手持POS照片审核通过
                            case "11":  //手持POS照片审核不通过
                            case "12":
                            case "13":
                            case "7":   //完成
                                Intent intent = new Intent(activity, ApplyPosActivity.class);
                                intent.putExtra("type", Integer.parseInt(json.optString("tua_status")));
                                activity.startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    ToastUtils.show(activity, baseBean.getRespDesc());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(activity.getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }
    //前往开通会员弹窗
    public static void showDialog(final Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage("请先开通落地POS")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, UpgradeVipActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .setNeutralButton("取消", null)
                .create();
        dialog.show();
    }

    @Override
    public void initData() {

        int type = getIntent().getIntExtra("type", -1);

        switch (type) {
            case 0: //已付款
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ApplyPosSecondFragment()).commit();
                break;
            case 1://   审核中
                PosApplyStatusFragment fragment1 = new PosApplyStatusFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", PosApplyStatusFragment.TYPE_DISPOSE_RUNNING);
                fragment1.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment1).commit();
                break;
            case 2: //审核通过
                PosApplyStatusFragment fragment2 = new PosApplyStatusFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", PosApplyStatusFragment.TYPE_DISPOSE_SUCCESS);
                fragment2.setArguments(bundle2);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment2).commit();
                break;
            case 3: //审核不通过
                PosApplyStatusFragment fragment3 = new PosApplyStatusFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type", PosApplyStatusFragment.TYPE_DISPOSE_FAIL);
                fragment3.setArguments(bundle3);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment3).commit();
                break;
            case 4: //审核关闭
                break;
            case 5: //已寄出
                PosApplyStatusFragment fragment5 = new PosApplyStatusFragment();
                Bundle bundle5 = new Bundle();
                bundle5.putInt("type", PosApplyStatusFragment.TYPE_HAVE_MAIL);
                fragment5.setArguments(bundle5);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment5).commit();
                break;
            case 9: //手持POS照片提交成功
                PosApplyStatusFragment fragment6 = new PosApplyStatusFragment();
                Bundle bundle6 = new Bundle();
                bundle6.putInt("type", PosApplyStatusFragment.TYPE_HAND_POS_DISPOSE_RUNNING);
                fragment6.setArguments(bundle6);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment6).commit();
                break;
            case 10:
            case 7:
                PosApplyStatusFragment fragment61 = new PosApplyStatusFragment();
                Bundle bundle61 = new Bundle();
                bundle61.putInt("type", PosApplyStatusFragment.TYPE_HAND_POS_DISPOSE_SUCCESS);
                fragment61.setArguments(bundle61);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment61).commit();
                break;
            case 11:
                PosApplyStatusFragment fragment62 = new PosApplyStatusFragment();
                Bundle bundle62 = new Bundle();
                bundle62.putInt("type", PosApplyStatusFragment.TYPE_HAND_POS_DISPOSE_FAIL);
                fragment62.setArguments(bundle62);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment62).commit();
                break;
        }

    }

    @Override
    public void initView() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    //跳转到审核中
    public void startToPosDisposeRunning() {
        PosApplyStatusFragment fragment = new PosApplyStatusFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", PosApplyStatusFragment.TYPE_DISPOSE_RUNNING);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_fragment_enter, R.anim.anim_fragment_exit)
                .replace(R.id.frame_layout, fragment).commit();
    }

    //跳转回到申请POS填写资料页面
    public void startToApplyPos() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_fragment_enter, R.anim.anim_fragment_exit)
                .replace(R.id.frame_layout, new ApplyPosSecondFragment()).commit();
    }

    //跳转到手持POS照片审核中
    public void startToHandPosDisposeRunning() {
        PosApplyStatusFragment fragment = new PosApplyStatusFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", PosApplyStatusFragment.TYPE_HAND_POS_DISPOSE_RUNNING);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_fragment_enter, R.anim.anim_fragment_exit)
                .replace(R.id.frame_layout, fragment).commit();
    }

}
