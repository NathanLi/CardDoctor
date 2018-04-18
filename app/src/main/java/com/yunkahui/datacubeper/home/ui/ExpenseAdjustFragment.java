package com.yunkahui.datacubeper.home.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.logic.ExpenseAdjustLogic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseAdjustFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ExpenseAdjustFragment";
    private ExpenseAdjustLogic mLogic;
    private List<String> mTypeList;
    private RadioGroup mRadioGroupType;
    private EditText mEditTextInputMoney;
    private int mIndex;

    @Override
    public void initData() {
        mLogic = new ExpenseAdjustLogic();
        mTypeList = new ArrayList<>();
        mEditTextInputMoney.setText(((AdjustPlanActivity) getActivity()).getAmount());
        loadData();
    }

    @Override
    public void initView(View view) {
        mRadioGroupType = view.findViewById(R.id.radio_group_type);
        mEditTextInputMoney = view.findViewById(R.id.edit_text_input_money);
        view.findViewById(R.id.button_submit).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_expense_adjust;
    }


    private void loadData() {
        LoadingViewDialog.getInstance().show(mActivity);
        mLogic.getMccList(mActivity, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    Log.e(TAG, "onSuccess: "+object.toString());
                    JSONArray array = object.getJSONArray("respData");
                    mTypeList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        mTypeList.add(array.getString(i));
                    }
                    updateRadioButton();
                    initView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Log.e(TAG, "onFailure: " + throwable.getMessage());
                Toast.makeText(mActivity, "连接超时", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRadioButton() {
        String type = ((AdjustPlanActivity) getActivity()).getBusinessType();
        for (int i = 0; i < mTypeList.size(); i++) {
            if (mTypeList.get(i).equals(type)) {
                mIndex = i;
            }
        }
    }

    private void initView() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams lineParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        int paddingH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getActivity().getResources().getDisplayMetrics());
        int paddingV = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getActivity().getResources().getDisplayMetrics());
        int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getActivity().getResources().getDisplayMetrics());

        mRadioGroupType.removeAllViews();

        int[] colors = new int[]{Color.parseColor("#888888"), Color.parseColor("#007AFF")};
        int[][] states = new int[2][];
        states[0] = new int[]{-android.R.attr.state_checked};
        states[1] = new int[]{android.R.attr.state_checked};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        Bitmap a = null;
        for (int i = 0; i < mTypeList.size(); i++) {
            Drawable drawableSelect = getActivity().getResources().getDrawable(R.drawable.bg_radio_button_tick_selector);
            drawableSelect.setBounds(0, 0, drawableSelect.getMinimumWidth(), drawableSelect.getMinimumHeight());
            RadioButton button = new RadioButton(getActivity());
            button.setPadding(paddingH, paddingV, paddingH, paddingV);
            button.setId(i);
            if (i == 0) {
                button.setChecked(true);
            }
            button.setButtonDrawable(new BitmapDrawable(a));
            button.setText(mTypeList.get(i));
            button.setLayoutParams(params);
            button.setTextSize(textSize);
            button.setCompoundDrawables(null, null, drawableSelect, null);
            button.setTextColor(colorStateList);
            //添加下面黑线
            View view = new View(getActivity());
            view.setLayoutParams(lineParams);
            view.setBackgroundColor(Color.parseColor("#DDDDDD"));

            mRadioGroupType.addView(button);
            mRadioGroupType.addView(view);
        }
        mRadioGroupType.check(mIndex);
        mRadioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mIndex = checkedId;
            }
        });

    }

    private void submit() {
        if (mTypeList.size() == 0) {
            Toast.makeText(getActivity(), "连接错误", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingViewDialog.getInstance().show(mActivity);
        mLogic.updatePlanningInfo(mActivity, ((AdjustPlanActivity) getActivity()).getId(), mTypeList.get(mIndex), mEditTextInputMoney.getText().toString(), new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                Intent intent = new Intent()
                        .putExtra("amount", mEditTextInputMoney.getText().toString())
                        .putExtra("business_type", mTypeList.get(mIndex))
                        .putExtra("type", "expense");
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (TextUtils.isEmpty(mEditTextInputMoney.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入消费金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                submit();
                break;
        }
    }
}
