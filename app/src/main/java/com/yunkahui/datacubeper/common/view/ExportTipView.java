package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * @author WYF on 2018/4/23/023.
 */
public class ExportTipView extends FrameLayout {

    private EditText mEtMsg;
    private TextView mTvTip;

    public ExportTipView(@NonNull Context context) {
        this(context, null);
    }

    public ExportTipView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExportTipView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_export_tip, this);
        mEtMsg = view.findViewById(R.id.et_msg);
        mTvTip = view.findViewById(R.id.tv_tip);
    }

    public TextView getTip() {
        return mTvTip;
    }

    public EditText getMsg() {
        return mEtMsg;
    }
}
