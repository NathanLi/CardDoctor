package com.yunkahui.datacubeper.upgradeJoin.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * VIP套餐说明
 */
public class VipInstructionActivity extends AppCompatActivity implements IActivityStatusBar{

    private TextView mTextViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_vip_instruction);
        super.onCreate(savedInstanceState);
        mTextViewText=findViewById(R.id.text_view_text);
        String text=getIntent().getStringExtra("text");
        mTextViewText.setText(Html.fromHtml(text));
        initAction();
    }

    private void initAction(){
        ActionBar actionBar = getSupportActionBar();
        this.setTitle("");
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {finish();} break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
