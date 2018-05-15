package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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
    private EditText mEtInput;
    private View mLlInfo;
    private TextView mTvInput;
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
        TextView tvTitle = view.findViewById(R.id.tv_title);
        mEtInput = view.findViewById(R.id.et_input);
        mTvDest = view.findViewById(R.id.tv_dest);
        ImageView ivIcon = view.findViewById(R.id.iv_icon);
        mLlInfo = view.findViewById(R.id.ll_info);
        mTvInput = view.findViewById(R.id.tv_input);
        View line = view.findViewById(R.id.line_indicator);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.InfoFillView, defStyleAttr, 0);
        String name = a.getString(R.styleable.InfoFillView_infoName);
        String dest = a.getString(R.styleable.InfoFillView_infoDest);
        String hint = a.getString(R.styleable.InfoFillView_inputHint);
        boolean isTextView = a.getBoolean(R.styleable.InfoFillView_isTextView, false);
        int imgRes = a.getResourceId(R.styleable.InfoFillView_infoIcon, 0);
        int editTextVisible = a.getInt(R.styleable.InfoFillView_inputVisible, INVISIBLE);
        int destColor = a.getColor(R.styleable.InfoFillView_destColor, getResources().getColor(R.color.text_color_deep_gray_656565));
        line.setVisibility(a.getInt(R.styleable.InfoFillView_lineVisible, GONE));
        tvTitle.setText(name);
        mTvDest.setText(dest);
        mEtInput.setHint(hint);
        mEtInput.setHintTextColor(Color.parseColor("#cccccc"));
        mEtInput.setVisibility(editTextVisible);
        mTvDest.setTextColor(destColor);
        ivIcon.setBackgroundResource(imgRes);
        if (isTextView) {
            mTvInput.setVisibility(VISIBLE);
            mEtInput.setVisibility(GONE);
        } else {
            mTvInput.setVisibility(GONE);
            mEtInput.setVisibility(VISIBLE);
        }
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (watcher != null)
                    watcher.beforeTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (watcher != null)
                    watcher.onTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (watcher != null)
                    watcher.afterTextChanged(editable);
            }
        });
        a.recycle();
    }

    public void setInputVisibility(boolean isVisibility) {
        mEtInput.setVisibility(isVisibility ? VISIBLE : GONE);
        mTvInput.setVisibility(isVisibility ? VISIBLE : GONE);
    }

    public void setOnClickListener(OnClickListener l) {
        mLlInfo.setOnClickListener(l);
    }

    public String getEditText() {
        return mEtInput.getText().toString().trim();
    }

    public String getDest() {
        return mTvDest.getText().toString().trim();
    }

    public void setDestColor(int color) {
        mTvDest.setTextColor(color);
    }

    public void setText(String text){
        mEtInput.setText(text);
    }

    public void setDest(String dest) {
        if (dest == null) {
            return;
        }
        mTvDest.setText(dest);
    }

    public String getName() {
        return mTvInput.getText().toString().trim();
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }
        mTvInput.setText(name);
    }

    public EditText getEtInput(){
        return mEtInput;
    }

    public void setCursorVisible(boolean visible) {
        mEtInput.setCursorVisible(visible);
    }

    public void setOnEditTextTouchListener(OnTouchListener l) {
        mEtInput.setOnTouchListener(l);
    }

    public void addTextChangeListener(TextWatcher watcher) {
        this.watcher = watcher;
    }
}
