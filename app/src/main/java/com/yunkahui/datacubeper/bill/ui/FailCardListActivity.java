package com.yunkahui.datacubeper.bill.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.logic.FailCardListLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;

//交易关闭卡片列表页面
public class FailCardListActivity extends AppCompatActivity implements IActivityStatusBar {

    private TextView mTextViewTips;
    private RecyclerView mRecyclerViewFailCard;
    private FailCardListLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fail_card_list);
        super.onCreate(savedInstanceState);
        setTitle("交易关闭卡片");
    }

    @Override
    public void initData() {
        mLogic = new FailCardListLogic();
        int num = getIntent().getIntExtra("num", 0);
        mTextViewTips.setText(Html.fromHtml(String.format(getResources().getString(R.string.fail_card_list_tips), num)));

        loadData();
    }

    @Override
    public void initView() {
        mTextViewTips = findViewById(R.id.text_view_tips);
        mRecyclerViewFailCard = findViewById(R.id.recycler_view);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    private void loadData() {
        mLogic.loadFailCardList(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("失败卡片列表->" + baseBean.getJsonObject().toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }


}
