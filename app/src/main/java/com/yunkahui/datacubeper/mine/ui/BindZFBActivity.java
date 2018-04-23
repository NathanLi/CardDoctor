package com.yunkahui.datacubeper.mine.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.mine.logic.MyZFBLogic;

import org.json.JSONException;
import org.json.JSONObject;

//绑定支付宝
public class BindZFBActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private SimpleEditTextView mEditTextViewZFB;
    private SimpleEditTextView mEditTextViewName;
    private Button mButtonSubmit;

    private MyZFBLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_zfb);
        super.onCreate(savedInstanceState);
        setTitle("绑定支付宝");
    }

    @Override
    public void initData() {
        mLogic=new MyZFBLogic();
    }

    @Override
    public void initView() {
        mEditTextViewZFB=findViewById(R.id.simple_input_item_zfb);
        mEditTextViewName=findViewById(R.id.simple_input_item_name);
        mButtonSubmit=findViewById(R.id.button_submit);

        mEditTextViewZFB.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mEditTextViewName.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mButtonSubmit.setOnClickListener(this);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    private class InnerTextChangeListener extends CustomTextChangeListener{
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(TextUtils.isEmpty(mEditTextViewName.getText())||TextUtils.isEmpty(mEditTextViewZFB.getText())){
                mButtonSubmit.setBackgroundColor(getResources().getColor(R.color.bg_button_gray_a9a9a9));
                mButtonSubmit.setEnabled(false);
            }else{
                mButtonSubmit.setBackgroundResource(R.drawable.bg_button_blue_selector);
                mButtonSubmit.setEnabled(true);
            }


        }
    }


    private void bindZFB(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.bindZFB(this, mEditTextViewZFB.getText(), mEditTextViewName.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("绑定支付宝->"+baseBean.getJsonObject().toString());
                try {
                    JSONObject object=baseBean.getJsonObject();
                    ToastUtils.show(getApplicationContext(),object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        setResult(RESULT_OK);
                        finish();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                bindZFB();
                break;
        }
    }
}
