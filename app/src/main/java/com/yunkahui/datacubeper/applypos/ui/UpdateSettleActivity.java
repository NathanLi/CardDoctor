package com.yunkahui.datacubeper.applypos.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.PosManageLogic;
import com.yunkahui.datacubeper.applypos.logic.UpdateSettleLogic;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.DialogSub;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.common.view.SimpleMenuItemView;
import com.yunkahui.datacubeper.common.view.SimpleTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//更变结算信息
public class UpdateSettleActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE_BRANCH = 1001;
    private final int RESULT_CODE_ID_CARD = 1002;
    private final int RESULT_CODE_BANK_CARD = 1003;
    private final int RESULT_CODE_HAND_BANK_CARD = 1004;

    private SimpleEditTextView mEditTextViewAccountName;
    private SimpleEditTextView mEditTextViewNewBankCard;
    private SimpleEditTextView mEditTextViewBankCardName;
    private SimpleTextView mSimpleTextViewBranchAddress;
    private SimpleTextView mSimpleTextViewBranch;
    private SimpleEditTextView mEditTextViewBranchNumber;
    private SimpleMenuItemView mMenuItemViewIdCard;
    private SimpleMenuItemView mMenuItemViewBankCard;
    private SimpleMenuItemView mMenuItemViewHandBankCard;

    private DialogSub mDialogArea;
    private String mProvince;
    private String mCity;
    private String mIdCardPath;
    private String mBankCardPath;
    private String mHandBankCardPath;
    private UpdateSettleLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_settle);
        super.onCreate(savedInstanceState);
        setTitle("更变结算信息");

    }

    @Override
    public void initData() {
        mLogic = new UpdateSettleLogic();
        mDialogArea = new DialogSub(this);
        String name = getIntent().getStringExtra("name");
        mEditTextViewAccountName.setText(name);
    }

    @Override
    public void initView() {
        mEditTextViewAccountName = findViewById(R.id.simple_input_item_name);
        mEditTextViewNewBankCard = findViewById(R.id.simple_input_item_bank_card_number);
        mEditTextViewBankCardName = findViewById(R.id.simple_input_item_bank_card_name);
        mSimpleTextViewBranchAddress = findViewById(R.id.simple_text_view_branch_area);
        mSimpleTextViewBranch = findViewById(R.id.simple_text_view_branch_name);
        mEditTextViewBranchNumber = findViewById(R.id.simple_input_item_branch_number);
        mMenuItemViewIdCard = findViewById(R.id.simple_menu_id_card_photo);
        mMenuItemViewBankCard = findViewById(R.id.simple_menu_bank_card_photo);
        mMenuItemViewHandBankCard = findViewById(R.id.simple_menu_hand_bank_card_photo);

        findViewById(R.id.button_submit).setOnClickListener(this);
        mSimpleTextViewBranch.setOnClickListener(this);
        mSimpleTextViewBranchAddress.setOnClickListener(this);
        mMenuItemViewIdCard.setOnClickListener(this);
        mMenuItemViewBankCard.setOnClickListener(this);
        mMenuItemViewHandBankCard.setOnClickListener(this);

        mEditTextViewNewBankCard.getEditTextInput().addTextChangedListener(new CustomTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    checkBankCardName();
                }
            }
        });

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    private void checkBankCardName() {
        new PosManageLogic().checkBankCardName(this, mEditTextViewNewBankCard.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("查询所属银行->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    String bankName = object.optJSONObject("respData").optString("bankName");
                    String bankNameBin = object.optJSONObject("respData").optString("bankNameEn");
                    mEditTextViewBankCardName.setText(bankName);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(UpdateSettleActivity.this, "查询所属银行失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_CODE_BRANCH) {
                mSimpleTextViewBranch.setText(data.getStringExtra("bank_name"));
                mEditTextViewBranchNumber.setText(data.getStringExtra("bank_cnaps"));
            } else if (requestCode == RESULT_CODE_ID_CARD) {
                mIdCardPath = data.getStringExtra("image");
            } else if (requestCode == RESULT_CODE_BANK_CARD) {
                mBankCardPath = data.getStringExtra("image");
            } else if (requestCode == RESULT_CODE_HAND_BANK_CARD) {
                mHandBankCardPath = data.getStringExtra("image");
            }
            updateUI();
        }
    }

    private void updateUI() {
        if (!TextUtils.isEmpty(mIdCardPath)) {
            mMenuItemViewIdCard.setRightIcon(R.mipmap.ic_icon_radio_select);
        }
        if (!TextUtils.isEmpty(mBankCardPath)) {
            mMenuItemViewBankCard.setRightIcon(R.mipmap.ic_icon_radio_select);
        }
        if (!TextUtils.isEmpty(mHandBankCardPath)) {
            mMenuItemViewHandBankCard.setRightIcon(R.mipmap.ic_icon_radio_select);
        }
    }

    //提交变更信息
    private void updateSettleData() throws JSONException {
        JSONArray array = new JSONArray();
        String[] types = new String[]{"0", "3", "6"};
        String[] paths = new String[]{mIdCardPath, mBankCardPath, mHandBankCardPath};
        for (int i = 0; i < types.length; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", types[i]);
            jsonObject.put("url", paths[i]);
            array.put(jsonObject);
        }
        LoadingViewDialog.getInstance().show(this);
        mLogic.updataSettleData(this,mEditTextViewAccountName.getText(), mEditTextViewNewBankCard.getText(), mEditTextViewBankCardName.getText(), mSimpleTextViewBranch.getText(),
                mProvince, mCity, mEditTextViewBranchNumber.getText(), array.toString(), new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        LoadingViewDialog.getInstance().dismiss();
                        LogUtils.e("变更结算信息-->" + baseBean.toString());
                        ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                        if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadingViewDialog.getInstance().dismiss();
                        ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (TextUtils.isEmpty(mEditTextViewNewBankCard.getText()) || TextUtils.isEmpty(mEditTextViewBankCardName.getText())
                        || TextUtils.isEmpty(mSimpleTextViewBranch.getText()) || TextUtils.isEmpty(mSimpleTextViewBranchAddress.getText())
                        || TextUtils.isEmpty(mEditTextViewBranchNumber.getText()) || TextUtils.isEmpty(mIdCardPath)
                        || TextUtils.isEmpty(mBankCardPath) || TextUtils.isEmpty(mHandBankCardPath)) {
                    ToastUtils.show(getApplicationContext(), "请完善信息");
                    return;
                }
                try {
                    updateSettleData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.simple_text_view_branch_area:
                mDialogArea.showLocalCityPicker(new DialogSub.CityPickerListener() {
                    @Override
                    public void picker(String province, String city) {
                        mProvince = province;
                        mCity = city;
                        mSimpleTextViewBranchAddress.setText(mProvince + " " + mCity);
                    }
                });
                break;
            case R.id.simple_menu_id_card_photo:
                Intent intentIdCard = new Intent(this, UpLoadImageActivity.class);
                intentIdCard.putExtra("type", UpLoadImageActivity.TYPE_SETTLE_ID_CARD);
                intentIdCard.putExtra("image", mIdCardPath);
                startActivityForResult(intentIdCard, RESULT_CODE_ID_CARD);
                break;
            case R.id.simple_menu_bank_card_photo:
                Intent intentBankCard = new Intent(this, UpLoadImageActivity.class);
                intentBankCard.putExtra("type", UpLoadImageActivity.TYPE_SETTLE_BANK_CARD);
                intentBankCard.putExtra("image", mBankCardPath);
                startActivityForResult(intentBankCard, RESULT_CODE_BANK_CARD);
                break;
            case R.id.simple_menu_hand_bank_card_photo:
                Intent intentHand = new Intent(this, UpLoadImageActivity.class);
                intentHand.putExtra("type", UpLoadImageActivity.TYPE_SETTLE_HANK_BAND_CARD);
                intentHand.putExtra("image", mHandBankCardPath);
                startActivityForResult(intentHand, RESULT_CODE_HAND_BANK_CARD);
                break;
            case R.id.simple_text_view_branch_name:
                if (TextUtils.isEmpty(mEditTextViewNewBankCard.getText()) || TextUtils.isEmpty(mEditTextViewBankCardName.getText()) || TextUtils.isEmpty(mSimpleTextViewBranchAddress.getText())) {
                    ToastUtils.show(getApplicationContext(), "请先完善信息");
                } else {
                    Intent intent = new Intent(this, BranchInformationActivity.class);
                    intent.putExtra("card_number", mEditTextViewNewBankCard.getText());
                    intent.putExtra("bank_name", mEditTextViewBankCardName.getText());
                    intent.putExtra("deposit_province", mProvince);
                    intent.putExtra("deposit_city", mCity);
                    startActivityForResult(intent, RESULT_CODE_BRANCH);
                }
                break;
        }
    }
}
