package com.yunkahui.datacubeper.upgradeJoin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

//升级加盟
public class UpgradeJoinActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener, UpgradeJoinItemView.OnChildClickListener {

    private UpgradeJoinItemView mUpgradeJoinItemView1;
    private UpgradeJoinItemView mUpgradeJoinItemView2;
    private UpgradeJoinItemView mUpgradeJoinItemView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_upgrade_join);
        super.onCreate(savedInstanceState);
        setTitle("升级加盟");
    }

    @Override
    public void initView() {
        mUpgradeJoinItemView1=findViewById(R.id.upgrade_join_item_view1);
        mUpgradeJoinItemView2=findViewById(R.id.upgrade_join_item_view2);
        mUpgradeJoinItemView3=findViewById(R.id.upgrade_join_item_view3);

        mUpgradeJoinItemView1.setOnClickListener(this);
        mUpgradeJoinItemView2.setOnClickListener(this);
        mUpgradeJoinItemView3.setOnClickListener(this);
        mUpgradeJoinItemView1.setOnChildClickListener(this);
        mUpgradeJoinItemView2.setOnChildClickListener(this);
        mUpgradeJoinItemView3.setOnChildClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,UpgradeJoinIntroduceActivity.class);
        switch (v.getId()){
            case R.id.upgrade_join_item_view1:
                intent.putExtra("type",UpgradeJoinIntroduceActivity.TYPE_VIP);
                break;
            case R.id.upgrade_join_item_view2:
                intent.putExtra("type",UpgradeJoinIntroduceActivity.TYPE_AGENT);
                break;
            case R.id.upgrade_join_item_view3:
                intent.putExtra("type",UpgradeJoinIntroduceActivity.TYPE_OEM);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onChildClick(View parent, View view) {
        switch (parent.getId()){
            case R.id.upgrade_join_item_view1:
                startActivity(new Intent(this,UpgradeVipActivity.class));
                break;
            case R.id.upgrade_join_item_view2:
                break;
            case R.id.upgrade_join_item_view3:
                break;
        }
    }
}
