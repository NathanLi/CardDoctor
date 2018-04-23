package com.yunkahui.datacubeper.upgradeJoin.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.AgentType;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.DialogSub;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.upgradeJoin.logic.AgentApplyLogic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//代理商申请
public class AgentApplyActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    public static final int TYPE_AGENT=1;
    public static final int TYPE_OEM=2;

    private int mType;
    private Spinner mSpinnerType;
    private SimpleEditTextView mEditTextViewName;
    private SimpleEditTextView mEditTextViewPhone;
    private SimpleEditTextView mEditTextViewWX;
    private TextView mTextViewCity;
    private EditText mEditTextRemark;

    private AgentApplyLogic mLogic;
    private List<AgentType> mAgentTypes;
    private List<String> mAgentTypeNames;
    private ArrayAdapter<String> mSpinnerAdapter;

    private String mAgentTypeId;
    private DialogSub dialogSub;

    private String mProvinceName;
    private String mCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_agent_apply);
        super.onCreate(savedInstanceState);
        setTitle("我要申请");
    }

    @Override
    public void initData() {
        mLogic=new AgentApplyLogic();
        mType=getIntent().getIntExtra("type",TYPE_AGENT);
        mAgentTypes=new ArrayList<>();
        mAgentTypeNames=new ArrayList<>();
        dialogSub = new DialogSub(this);

        mSpinnerAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,mAgentTypeNames);
        mSpinnerType.setAdapter(mSpinnerAdapter);
        mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAgentTypeId=mAgentTypes.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(mType==TYPE_AGENT){
            loadAgentNickName();
        }else{
            AgentType type=new AgentType();
            type.setId("02");
            type.setName("机构");
            mAgentTypes.add(type);
            mAgentTypeNames.add("机构");
            mSpinnerAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void initView() {
        mSpinnerType=findViewById(R.id.spinner_type);
        mEditTextViewName=findViewById(R.id.simple_input_item_name);
        mEditTextViewPhone=findViewById(R.id.simple_input_item_phone);
        mEditTextViewWX=findViewById(R.id.simple_input_item_weixin);
        mTextViewCity=findViewById(R.id.text_view_city);
        mEditTextRemark=findViewById(R.id.edit_text_remark);


        mTextViewCity.setOnClickListener(this);
        findViewById(R.id.button_submit).setOnClickListener(this);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    private void loadAgentNickName(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadAgentNickName(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("代理商昵称->"+baseBean.getJsonObject().toString());
                try {
                    JSONObject object=baseBean.getJsonObject();
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        JSONObject json=object.optJSONObject("respData");
                        mAgentTypes.clear();
                        for (Iterator<String> it = json.keys(); it.hasNext();) {
                            String key = it.next();
                            LogUtils.e("key="+key);
                            AgentType type=new AgentType();
                            type.setId(key);
                            type.setName(json.optString(key));
                            mAgentTypes.add(type);
                            mAgentTypeNames.add(json.optString(key));
                        }
                        mSpinnerAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"获取代理商信息失败");
                LogUtils.e("代理商昵称失败->"+throwable.toString());
            }
        });
    }

    private boolean check(){
        if(TextUtils.isEmpty(mAgentTypeId)){
            ToastUtils.show(getApplicationContext(),"请选择申请类型");
            return false;
        }
        if(TextUtils.isEmpty(mEditTextViewName.getText())){
            ToastUtils.show(getApplicationContext(),"请输入姓名");
            return false;
        }
        if(TextUtils.isEmpty(mEditTextViewPhone.getText())){
            ToastUtils.show(getApplicationContext(),"请输入手机号");
            return false;
        }
        if(TextUtils.isEmpty(mProvinceName)){
            ToastUtils.show(getApplicationContext(),"请选择所在城市");
            return false;
        }
        return true;
    }

    /**
     * 提交代理商或OEM申请
     */
    private void submitAgentApply(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.submitAgentApply(this, mAgentTypeId, mEditTextViewName.getText(), mEditTextViewPhone.getText(), mEditTextViewWX.getText(),
                mProvinceName, mCityName, mEditTextRemark.getText().toString(), new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        LoadingViewDialog.getInstance().dismiss();
                        try {
                            LogUtils.e("我要申请->"+baseBean.getJsonObject().toString());
                            JSONObject object=baseBean.getJsonObject();
                            ToastUtils.show(getApplicationContext(),object.optString("respDesc"));
                            if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadingViewDialog.getInstance().dismiss();
                        ToastUtils.show(getApplicationContext(),"申请请求失败");
                        LogUtils.e("申请请求失败->"+throwable.toString());
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_view_city:
                dialogSub.showLocalCityPicker(new DialogSub.CityPickerListener() {
                    @Override public void picker(String province, String city) {
                        mProvinceName = province;
                        mCityName = city;
                        mTextViewCity.setText(mProvinceName+" "+mCityName);
                    }
                });
                break;
            case R.id.button_submit:
                if(check()){
                    submitAgentApply();
                }
                break;
        }
    }
}
