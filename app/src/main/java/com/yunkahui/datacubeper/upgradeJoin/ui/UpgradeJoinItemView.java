package com.yunkahui.datacubeper.upgradeJoin.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.VipPackage;

/**
 * Created by Administrator on 2018/3/23.
 */

public class UpgradeJoinItemView extends RelativeLayout implements View.OnClickListener {

    public static final int BUTTON_SUBMIT = R.id.text_view_submit;    //右边按钮

    private LinearLayout mLinearLayoutIcon;
    private TextView mTextViewIconTitle;
    private TextView mTextViewIconSubTitle;

    private TextView mTextViewTitle;
    private TextView mTextViewSubTitle;
    private TextView mTextViewSubmit;

    private OnChildClickListener mClickListener;

    public UpgradeJoinItemView(Context context) {
        this(context, null);
    }

    public UpgradeJoinItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpgradeJoinItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_upgrade_to_join_item_view, this);

        mLinearLayoutIcon = findViewById(R.id.linear_layout_icon);
        mTextViewIconTitle = findViewById(R.id.text_view_icon_title);
        mTextViewIconSubTitle = findViewById(R.id.text_view_icon_sub_title);

        mTextViewTitle = findViewById(R.id.tv_title);
        mTextViewSubTitle = findViewById(R.id.text_view_sub_title);
        mTextViewSubmit = findViewById(R.id.text_view_submit);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UpgradeJoinItemView);

        if (ta.hasValue(R.styleable.UpgradeJoinItemView_uj_icon_background_color)) {
            mLinearLayoutIcon.setBackgroundColor(Color.parseColor(ta.getString(R.styleable.UpgradeJoinItemView_uj_icon_background_color)));
        }
        if (ta.hasValue(R.styleable.UpgradeJoinItemView_uj_icon)) {
            mLinearLayoutIcon.setBackgroundResource(ta.getResourceId(R.styleable.UpgradeJoinItemView_uj_icon, 0));
        }

        mTextViewIconTitle.setText(ta.getString(R.styleable.UpgradeJoinItemView_uj_icon_title));
        mTextViewIconSubTitle.setText(ta.getString(R.styleable.UpgradeJoinItemView_uj_icon_sub_title));

        mTextViewTitle.setText(ta.getString(R.styleable.UpgradeJoinItemView_uj_title));
        mTextViewSubTitle.setText(ta.getString(R.styleable.UpgradeJoinItemView_uj_sub_title));
        mTextViewSubmit.setText(ta.getString(R.styleable.UpgradeJoinItemView_uj_button_text));
        ta.recycle();

        mTextViewSubmit.setOnClickListener(this);
    }

    public void setIconTitle(String text) {
        mTextViewIconTitle.setText(text);
    }

    public void setIconSubTitle(String text) {
        mTextViewIconSubTitle.setText(text);
    }

    public void setData(VipPackage vipPackage) {
        if (vipPackage != null) {
            mTextViewTitle.setText(vipPackage.getName());
            mTextViewSubTitle.setText(vipPackage.getShortDesc());
            mTextViewIconTitle.setText((vipPackage.getPrice() + "").split("\\.")[0] + "元");
            mTextViewIconSubTitle.setText(vipPackage.getAlias());

            switch (vipPackage.getOpenState()) {
                case "00":
                    mTextViewSubmit.setText("开通");
                    mTextViewSubmit.setEnabled(true);
                    break;
                case "01":
                    mTextViewSubmit.setText("申请中");
                    mTextViewSubmit.setBackgroundResource(R.drawable.ic_circle_shape_3);
                    mTextViewSubmit.setEnabled(false);
                    break;
                case "02":
                    mTextViewSubmit.setText("已开通");
                    mTextViewSubmit.setBackgroundResource(R.drawable.ic_circle_shape_3);
                    mTextViewSubmit.setEnabled(false);
                    break;
                case "03":
                    mTextViewSubmit.setText("已禁止");
                    mTextViewSubmit.setBackgroundResource(R.drawable.ic_circle_shape_3);
                    mTextViewSubmit.setEnabled(false);
                    break;
            }

        }
    }

    public TextView getButton() {
        return mTextViewSubmit;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_submit:
                if (mClickListener != null) {
                    mClickListener.onChildClick(this, v);
                }
                break;
        }
    }

    public void setOnChildClickListener(OnChildClickListener listener) {
        mClickListener = listener;
    }

    public interface OnChildClickListener {
        void onChildClick(View parent, View view);
    }


}
