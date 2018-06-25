package com.yunkahui.datacubeper.applyreceipt.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.ui.UpLoadBankCardFragment;
import com.yunkahui.datacubeper.applypos.ui.UpLoadHandIdCardFragment;
import com.yunkahui.datacubeper.applypos.ui.UpLoadHandPosFragment;
import com.yunkahui.datacubeper.applypos.ui.UpLoadIdCardFragment;
import com.yunkahui.datacubeper.applypos.ui.UpLoadSettleImageFragment;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * Created by Administrator on 2018/6/25 0025
 */

public class UploadPhotoActivity extends AppCompatActivity implements IActivityStatusBar {

    public static final int TYPE_ID_CARD=101;
    public static final int TYPE_HAND_ID_CARD=102;
    public static final int TYPE_BANK_CARD=103;
    public static final int TYPE_HAND_POS=104;
    public static final int TYPE_HAND_BANK_CARD=105;

    public static final int TYPE_SETTLE_ID_CARD=106;
    public static final int TYPE_SETTLE_BANK_CARD=107;
    public static final int TYPE_SETTLE_HANK_BAND_CARD=108;

    private int mType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_up_load_image);
        super.onCreate(savedInstanceState);
        setTitle("上传照片");
    }

    @Override
    public void initData() {
        mType=getIntent().getIntExtra("type",0);
        switch (mType){
            case TYPE_ID_CARD:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new UpLoadIdCardReceiptFragment()).commit();
                break;
            case TYPE_HAND_ID_CARD:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new UpLoadHandIdCardReceiptFragment()).commit();
                break;
            case TYPE_BANK_CARD:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new UpLoadBankCardReceiptFragment()).commit();
                break;
            case TYPE_HAND_BANK_CARD:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new UploadHandBankCardReceiptFragment()).commit();
                break;
            case TYPE_HAND_POS:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new UpLoadHandPosFragment()).commit();
                break;
            case TYPE_SETTLE_ID_CARD:
            case TYPE_SETTLE_BANK_CARD:
            case TYPE_SETTLE_HANK_BAND_CARD:
                UpLoadSettleImageFragment fragment=new UpLoadSettleImageFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("type",mType);
                bundle.putString("image",getIntent().getStringExtra("image"));
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
                break;
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
