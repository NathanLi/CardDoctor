package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by pc1994 on 2018/3/23.
 */
public class CardSelector extends LinearLayout {

    private Context mContext;
    private RadioButton rbCard;

    public CardSelector(Context context) {
        this(context, null);
    }

    public CardSelector(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        init();
    }

    public CardSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_card_selector, this);
        rbCard = findViewById(R.id.rb_bank);
    }

    public void setText(String text) {
        rbCard.setText(text);
    }

    public boolean isChecked() {
        return rbCard.isChecked();
    }

    public void setChecked(boolean checked) {
        rbCard.setChecked(checked);
    }
}
