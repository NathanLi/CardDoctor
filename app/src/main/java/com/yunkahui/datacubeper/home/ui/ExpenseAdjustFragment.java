package com.yunkahui.datacubeper.home.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseAdjustFragment  {
//extends BaseFragment implements View.OnClickListener
    /*private List<String> mTypeList;
    private RadioGroup mRadioGroupType;
    private EditText mEditTextInputMoney;
    private int mIndex;

    @Override
    public void initData() {
        mTypeList=new ArrayList<>();
        mEditTextInputMoney.setText(((AdjustPlanActivity)getActivity()).getAmount());
        loadData();
    }

    @Override
    public void initView(View view) {
        mRadioGroupType=view.findViewById(R.id.radio_group_type);
        mEditTextInputMoney=view.findViewById(R.id.edit_text_input_money);
        view.findViewById(R.id.button_submit).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_expense_adjust;
    }


    private void loadData(){
        RemindUtil.remindHUD(getActivity());
        Map<String,String> param=new HashMap<>();
        RequestHelper helper=new RequestHelper(new SpecialConverterFactory(SpecialConverterFactory.ConverterType.Converter_Single));
        helper.getRequestApi().requestCommon(getString(R.string.slink_get_zhongfu_mcc_list),param)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TopBean>() {
            @Override public void onCompleted() {}
            @Override public void onError(Throwable e) {
                RemindUtil.dismiss();
                Toast.makeText(BaseApplication.getContext(), "连接超时", Toast.LENGTH_SHORT).show();
            }
            @Override public void onNext(TopBean topBean) {
                RemindUtil.dismiss();
                Log.e("2018","消费调整类型---》"+topBean.getResponse().toString());
                if(topBean.getCode()==RequestHelper.success){
                    try {
                        JSONArray array=topBean.getResponse().getJSONArray("respData");
                        mTypeList.clear();
                        for (int i=0;i<array.length();i++){
                            mTypeList.add(array.getString(i));
                        }
                        updateRadioButton();
                        initView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Toast.makeText(BaseApplication.getContext(), topBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateRadioButton(){
        String type;
        if(((AdjustActivity)getActivity()).isIntent()){
            type=((AdjustActivity)getActivity()).getDesignBean().getBusiness().getName();
        }else{
            type=((AdjustActivity)getActivity()).getBusinessType();
        }
        for (int i=0;i<mTypeList.size();i++){
            if(mTypeList.get(i).equals(type)){
                mIndex=i;
            }
        }
    }

    private void initView(){
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams lineParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        int paddingH= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15,getActivity().getResources().getDisplayMetrics());
        int paddingV= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getActivity().getResources().getDisplayMetrics());
        int textSize=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,8,getActivity().getResources().getDisplayMetrics());

        mRadioGroupType.removeAllViews();

        int [] colors=new int[]{Color.parseColor("#888888"),Color.parseColor("#007AFF")};
        int [][] states=new int[2][];
        states[0]=new int[]{-android.R.attr.state_checked};
        states[1]=new int[]{android.R.attr.state_checked};
        ColorStateList colorStateList =new ColorStateList(states,colors);
        Bitmap a=null;
        for (int i=0;i<mTypeList.size();i++){
            Drawable drawableSelect=getActivity().getResources().getDrawable(R.drawable.radio_button_tick_selector);
            drawableSelect.setBounds(0,0,drawableSelect.getMinimumWidth(),drawableSelect.getMinimumHeight());
            RadioButton button=new RadioButton(getActivity());
            button.setPadding(paddingH,paddingV,paddingH,paddingV);
            button.setId(i);
            if(i==0){
                button.setChecked(true);
            }
            button.setButtonDrawable(new BitmapDrawable(a));
            button.setText(mTypeList.get(i));
            button.setLayoutParams(params);
            button.setTextSize(textSize);
            button.setCompoundDrawables(null,null,drawableSelect,null);
            button.setTextColor(colorStateList);
            //添加下面黑线
            View view=new View(getActivity());
            view.setLayoutParams(lineParams);
            view.setBackgroundColor(Color.parseColor("#DDDDDD"));

            mRadioGroupType.addView(button);
            mRadioGroupType.addView(view);
        }
        mRadioGroupType.check(mIndex);
        mRadioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mIndex=checkedId;
            }
        });

    }

    private void submit(){
        if(mTypeList.size()==0){
            Toast.makeText(getActivity(),"连接错误",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!((AdjustActivity)getActivity()).isIntent()){
            Intent intent=new Intent();
            intent.putExtra("money",mEditTextInputMoney.getText().toString());
            intent.putExtra("business_type",mTypeList.get(mIndex));
            getActivity().setResult(Activity.RESULT_OK,intent);
            Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        RemindUtil.remindHUD(getActivity());
        String id=((AdjustActivity)getActivity()).getDesignBean().getId();
        Map<String,String> param=new HashMap<>();
        param.put("auto_planning_id",id);
        param.put("amount",mEditTextInputMoney.getText().toString());
        param.put("mccType",mTypeList.get(mIndex));
        param.put("version", BaseApplication.getVersion());
        RequestHelper requestHelper=new RequestHelper(new SpecialConverterFactory(SpecialConverterFactory.ConverterType.Converter_Single));
        requestHelper.getRequestApi().requestCommon(getString(R.string.slink_update_planning_info),param)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TopBean>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                RemindUtil.dismiss();
                Toast.makeText(BaseApplication.getContext(), "连接超时", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(TopBean topBean) {
                RemindUtil.dismiss();
                Log.e("2018","消费调整提交-->"+topBean.toString());
                if(topBean.getCode()==RequestHelper.success){
                    PosAdjustEvent event=new PosAdjustEvent();
                    event.setId(((AdjustActivity)getActivity()).getDesignBean().getId());
                    event.setType("xiaofei");
                    event.setEvent(PosAdjustEvent.UPDATE);
                    event.setBusinessName(mTypeList.get(mIndex));
                    event.setMoney(mEditTextInputMoney.getText().toString());
                    EventBus.getDefault().post(event);
                    getActivity().finish();
                }
                Toast.makeText(BaseApplication.getContext(),topBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                if(TextUtils.isEmpty(mEditTextInputMoney.getText().toString())){
                    Toast.makeText(getActivity(),"请输入消费金额",Toast.LENGTH_SHORT).show();
                    return;
                }
                submit();
                break;
        }
    }*/
}
