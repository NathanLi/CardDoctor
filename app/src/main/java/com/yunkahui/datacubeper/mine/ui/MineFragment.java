package com.yunkahui.datacubeper.mine.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.wang.avi.AVLoadingIndicatorView;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.ui.PosManageActivity;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.login.ui.LoginActivity;
import com.yunkahui.datacubeper.mine.logic.MessageLogic;
import com.yunkahui.datacubeper.upgradeJoin.ui.UpgradeJoinActivity;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.bean.PersonalInfo;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.mine.adapter.MineItemAdapter;
import com.yunkahui.datacubeper.mine.logic.MineLogic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private final int RESULT_CODE_IMAGE = 1001;
    private final int RESULT_CODE_UPDATE = 1002;

    private SimpleToolbar mSimpleToolbar;
    private RecyclerView mRecyclerView;
    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvRecommandCode;
    private TextView mTvReferee;
    private AVLoadingIndicatorView mLoadingIndicatorView;

    private MineItemAdapter mMineItemAdapter;
    private MineLogic mLogic;
    private List<MineItem> mMenuItemList;

    @Override
    public void initData() {
        mLogic = new MineLogic();
        mMenuItemList = new ArrayList<>();
        mMineItemAdapter = new MineItemAdapter(R.layout.layout_list_item_mine, mMenuItemList);
        mMineItemAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_header_mine, null);
        mIvIcon = headerView.findViewById(R.id.iv_user_icon);
        mTvName = headerView.findViewById(R.id.tv_user_name);
        mTvPhone = headerView.findViewById(R.id.tv_user_phone);
        mTvRecommandCode = headerView.findViewById(R.id.tv_recommand_code);
        mTvReferee = headerView.findViewById(R.id.tv_referee);

        mIvIcon.setOnClickListener(this);

        mMineItemAdapter.addHeaderView(headerView);
        mMineItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                itemClick(position);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mMineItemAdapter);

        mMenuItemList.addAll(mLogic.getMineItemList(getActivity()));
        mMineItemAdapter.notifyDataSetChanged();

        loadData();
    }


    @Override
    public void initView(View view) {
        mSimpleToolbar = view.findViewById(R.id.tool_bar);
        mSimpleToolbar.setTitleName(getString(R.string.mine));
        mSimpleToolbar.setRightIcon(R.mipmap.ic_icon_message_white);
        mSimpleToolbar.setRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLoadingIndicatorView = view.findViewById(R.id.av_loading_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkNewMessage();
        if (!TextUtils.isEmpty(DataUtils.getInfo().getUser_mobile())) {
            fillData(DataUtils.getInfo());
        }

    }

    //获取个人信息
    public void loadData() {
        mLoadingIndicatorView.setVisibility(View.VISIBLE);
        mLogic.loadPersonalInformation(getActivity(), new SimpleCallBack<BaseBean<PersonalInfo>>() {
            @Override
            public void onSuccess(BaseBean<PersonalInfo> personalInfoBaseBean) {
                mLoadingIndicatorView.setVisibility(View.GONE);
                if (RequestUtils.SUCCESS.equals(personalInfoBaseBean.getRespCode())) {
                    PersonalInfo info = personalInfoBaseBean.getRespData();
                    if (info != null) {
                        fillData(info);
                        DataUtils.setInfo(info);
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLoadingIndicatorView.setVisibility(View.GONE);
                ToastUtils.show(getActivity(), "个人信息获取失败");
            }
        });

    }

    //查询消息数量
    private void checkNewMessage() {

        mLogic.checkNewMessage(getActivity(), "", new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("消息->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    List<String> ids = mLogic.getIdListForMessage(baseBean.getJsonObject().optJSONObject("respData"));
                    int num = new MessageLogic().getUnReadMessageNumber(getActivity(), ids);
                    mSimpleToolbar.setAngle(num);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LogUtils.e("消息失败->" + throwable.toString());
            }
        });

    }


    private void fillData(PersonalInfo info) {
        mTvName.setText(info.getNickname());
        mTvPhone.setText(info.getUser_mobile());
        mTvRecommandCode.setText(info.getUser_unique_code());
        mTvReferee.setText(info.getParent_name());
        GlideApp.with(getActivity()).load(info.getAvatar()).error(R.mipmap.ic_header_normal)
                .transform(new CropCircleTransformation())
                .into(mIvIcon);

        if (mMenuItemList.size() > 3) {
            mMenuItemList.get(0).setDetail(info.getUser_role_text());
            mMenuItemList.get(1).setDetail(info.getIdentify_status().equals("1") ? "已实名" : "未实名");
            mMenuItemList.get(2).setDetail(info.getUser_mobile());
            PackageInfo packageInfo = null;
            try {
                packageInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
                mMenuItemList.get(11).setDetail(packageInfo.versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        mMineItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == RESULT_CODE_IMAGE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images.size() > 0) {
                    upLoadAvatar(images.get(0).path);
                }

            }
        }
        if (resultCode == getActivity().RESULT_OK && requestCode == RESULT_CODE_UPDATE) {
            loadData();
        }

    }

    /**
     * 上传头像
     */
    private void upLoadAvatar(final String path) {
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.upLoadAvatar(getActivity(), path, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("上传头像->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        GlideApp.with(getActivity()).load(path).error(R.mipmap.ic_header_normal)
                                .transform(new CropCircleTransformation())
                                .into(mIvIcon);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "上传头像失败");
            }
        });
    }

    //查询实名认证状态
    private void checkRealNameAuthStatus() {
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.checkRealNameAuthStatus(getActivity(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询实名认证状态->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        switch (object.optJSONObject("respData").optString("status")) {
                            case "0":
                                startActivity(new Intent(getActivity(), RealNameAuthActivity.class));
                                break;
                            case "1":
                                ToastUtils.show(getActivity(), "已实名认证");
                                break;
                            case "2":
                                ToastUtils.show(getActivity(), "正在审核认证中");
                                break;
                            case "3":
                                ToastUtils.show(getActivity(), "审核认证不成功，请重新认证");
                                startActivity(new Intent(getActivity(), RealNameAuthActivity.class));
                                break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询实名认证状态失败->" + throwable.toString());
            }
        });
    }

    public void itemClick(int position) {

        switch (mMenuItemList.get(position).getId()) {
            case 10:
                startActivity(new Intent(getActivity(), UpgradeJoinActivity.class));
                break;
            case 11:
                checkRealNameAuthStatus();
                break;
            case 12:
                startActivityForResult(new Intent(getActivity(), BindNewPhoneActivity.class), RESULT_CODE_UPDATE);
                break;
            case 13:
                startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
                break;
            case 14:
                startActivity(new Intent(getActivity(), EditPasswordActivity.class));
                break;
            case 20:
                break;
            case 21:
                break;
            case 30:
                startActivity(new Intent(mActivity, MyCashCardListActivity.class));
                break;
            case 31:
                startActivity(new Intent(mActivity, MyZFBActivity.class));
                break;
            case 32:
                startActivity(new Intent(mActivity, PosManageActivity.class));
                break;
            case 40:
                startActivity(new Intent(mActivity, AboutUsActivity.class));
                break;
            case 41:
                break;
            case 42:
                showExitDialog();
                break;
        }
    }

    private void showExitDialog() {
        final View codeView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_exit_dialog, null);
        final Dialog dialog = new Dialog(getActivity(), R.style.ExitDialog);
        dialog.setContentView(codeView);
        dialog.show();
        setDialogAttribute(getActivity(), dialog, 0.90, 0);
        codeView.findViewById(R.id.show_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        codeView.findViewById(R.id.show_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setDialogAttribute(Context context, Dialog dialog, double x, double y) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        Point middlePoint = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();
        display.getSize(middlePoint);
        if (x != 0) {
            params.width = x > 1 ? transFromDip((int) x) : (int) (middlePoint.x * x);
        }
        if (y != 0) {
            params.height = y > 1 ? transFromDip((int) y) : (int) (middlePoint.y * y);
        }
        dialogWindow.setAttributes(params);
    }

    public int transFromDip(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, CardDoctorApplication.getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_icon:
                ImagePicker.getInstance().setSelectLimit(1);
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, RESULT_CODE_IMAGE);
                break;
        }
    }
}