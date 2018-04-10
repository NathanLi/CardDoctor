package com.yunkahui.datacubeper.share.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseActivity;

/**
 * Created by YD1 on 2018/4/10
 */
public class ProfitActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"提现").setIcon(R.drawable.ic_withdrawals_text).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(this, ProfitWithdrawalsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profit;
    }
}
