package com.yunkahui.datacubeper.mine.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BaseBeanList;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.SimpleDateFormatUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import java.lang.ref.PhantomReference;
import java.util.Map;

//消息详情
public class MessageDetailActivity extends AppCompatActivity implements IActivityStatusBar{

    private TextView mTextViewTitle;
    private TextView mTextViewTime;
    private ImageView mImageViewMessage;
    private TextView mTextViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_detail);
        super.onCreate(savedInstanceState);
        setTitle("消息");
    }

    @Override
    public void initData() {
        String id=getIntent().getStringExtra("id");
        loadData(id);
    }

    @Override
    public void initView() {
        mTextViewTitle=findViewById(R.id.text_view_title);
        mTextViewTime=findViewById(R.id.text_view_time);
        mImageViewMessage=findViewById(R.id.image_view_message);
        mTextViewMessage=findViewById(R.id.text_view_message);
    }

    private void loadData(String id){
        LoadingViewDialog.getInstance().show(this);
        Map<String,String> params= RequestUtils.newParams(this)
                .addParams("news_id",id)
                .create();
        HttpManager.getInstance().create(ApiService.class).checkNewMessageById(params)
                .compose(HttpManager.<BaseBeanList<Message>>applySchedulers()).subscribe(new SimpleCallBack<BaseBeanList<Message>>() {
            @Override
            public void onSuccess(BaseBeanList<Message> messageBaseBeanList) {
                LoadingViewDialog.getInstance().dismiss();
                if(RequestUtils.SUCCESS.equals(messageBaseBeanList.getRespCode())){
                    Message message=messageBaseBeanList.getRespData().get(0);
                    mTextViewTitle.setText(message.getTitle());
                    mTextViewTime.setText(SimpleDateFormatUtils.formatYMDHS(Long.parseLong(message.getCreate_time())));
                    GlideApp.with(MessageDetailActivity.this).load(message.getContent_img()).thumbnail(0.1f).into(mImageViewMessage);
                    mTextViewMessage.setText(message.getContent_text());
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
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
