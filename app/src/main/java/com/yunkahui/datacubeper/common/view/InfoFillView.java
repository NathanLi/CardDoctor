package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by YD1 on 2018/4/12
 */
public class InfoFillView extends LinearLayout {

    private TextView mTvDest;
    private EditText mEtCardNum;
    private View mLlInfo;
    private TextView mTvName;
    private TextWatcher watcher;

    public InfoFillView(Context context) {
        this(context, null);
    }

    public InfoFillView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoFillView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_info_fill_view, this);
        TextView tvInfoName = view.findViewById(R.id.tv_info_name);
        mEtCardNum = view.findViewById(R.id.et_info);
        mTvDest = view.findViewById(R.id.tv_info_dest);
        ImageView ivInfoIcon = view.findViewById(R.id.iv_info_icon);
        mLlInfo = view.findViewById(R.id.ll_info);
        mTvName = view.findViewById(R.id.tv_info);
        View line = view.findViewById(R.id.line_indicator);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.InfoFillView, defStyleAttr, 0);
        String name = a.getString(R.styleable.InfoFillView_infoName);
        String dest = a.getString(R.styleable.InfoFillView_infoDest);
        String hint = a.getString(R.styleable.InfoFillView_inputHint);
        boolean isTextView = a.getBoolean(R.styleable.InfoFillView_isTextView, false);
        int imgRes = a.getResourceId(R.styleable.InfoFillView_infoIcon, 0);
        int inputVisible = a.getInt(R.styleable.InfoFillView_inputVisible, INVISIBLE);
        int destColor = a.getColor(R.styleable.InfoFillView_destColor, getResources().getColor(R.color.add_card_text_color_deep_gray_656565));

        line.setVisibility(a.getInt(R.styleable.InfoFillView_lineVisible, GONE));
        tvInfoName.setText(name);
        mTvDest.setText(dest);
        mEtCardNum.setHint(hint);
        mEtCardNum.setHintTextColor(Color.parseColor("#cccccc"));
        mEtCardNum.setVisibility(inputVisible);
        mTvDest.setTextColor(destColor);
        ivInfoIcon.setBackgroundResource(imgRes);
        if (isTextView) {
            mTvName.setVisibility(VISIBLE);
            mEtCardNum.setVisibility(GONE);
        } else {
            mTvName.setVisibility(GONE);
            mEtCardNum.setVisibility(VISIBLE);
        }
        mEtCardNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                watcher.beforeTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                watcher.onTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                watcher.afterTextChanged(editable);
            }
        });
        a.recycle();
    }

    public void setOnClickListener(OnClickListener l) {
        mLlInfo.setOnClickListener(l);
    }

    public String getCardNum() {
        return mEtCardNum.getText().toString().trim();
    }

    public String getDest() {
        return mTvDest.getText().toString().trim();
    }

    public void setDest(String dest) {
        if (dest == null) {
            return;
        }
        mTvDest.setText(dest);
    }

    public String getName() {
        return mTvName.getText().toString().trim();
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }
        mTvName.setText(name);
    }

    public void setCursorVisible(boolean visible) {
        mEtCardNum.setCursorVisible(visible);
    }

    public void setOnEditTextTouchListener(OnTouchListener l) {
        mEtCardNum.setOnTouchListener(l);
    }

    public void addTextChangeListener(TextWatcher watcher) {
        this.watcher = watcher;
    }
}
