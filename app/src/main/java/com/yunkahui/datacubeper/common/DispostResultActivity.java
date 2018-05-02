package com.yunkahui.datacubeper.common;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

public class DispostResultActivity extends AppCompatActivity {

    public static final int TYPE_TOP_UP=1;
    public static final int TYPE_WITHDRAW=2;

    private TextView mTextViewResult;
    private TextView mTextViewMoney;

    private int mType;
    private String mMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispost_result);

        mTextViewResult=findViewById(R.id.text_view_result);
        mTextViewMoney=findViewById(R.id.text_view_money);
        mType=getIntent().getIntExtra("type",0);
        mMoney=getIntent().getStringExtra("money");
        initActionBar();
        mTextViewMoney.setText("¥"+mMoney);
    }

    private void initActionBar(){
        switch (mType){
            case TYPE_TOP_UP:
                this.setTitle("充值结果");
                mTextViewResult.setText("充值处理中···");
                break;
            case TYPE_WITHDRAW:
                this.setTitle("提现结果");
                mTextViewResult.setText("提现处理中···");
                break;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"完成").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {finish();} break;
            case 1:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
