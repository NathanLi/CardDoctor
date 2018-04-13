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

    }


    @Override
    public void initView() {
        mTextViewText=findViewById(R.id.text_view_text);

    }

    @Override
    public void initData() {
        String text=getIntent().getStringExtra("text");
        mTextViewText.setText(Html.fromHtml(text));
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
