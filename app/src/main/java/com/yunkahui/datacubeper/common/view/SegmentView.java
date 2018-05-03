package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunkahui.datacubeper.R;

/**
 * Created by YD1 on 2018/4/12
 */
public class SegmentView extends LinearLayout implements View.OnClickListener {

    private ImageView mIvSegmentBack;
    private TextView mTvLeftSegment;
    private TextView mTvRightSegment;

    private OnSelectChangeLitener listener;

    public SegmentView(Context context) {
        this(context, null);
    }

    public SegmentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SegmentView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_segment_view, this);
        mIvSegmentBack = view.findViewById(R.id.iv_segment_back);
        mTvLeftSegment = view.findViewById(R.id.tv_left_segment);
        mTvRightSegment = view.findViewById(R.id.tv_right_segment);
        mTvLeftSegment.setOnClickListener(this);
        mTvRightSegment.setOnClickListener(this);
        mTvLeftSegment.setSelected(true);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SegmentView, defStyleAttr, 0);
        String leftSegment = a.getString(R.styleable.SegmentView_leftSegment);
        String rightSegment = a.getString(R.styleable.SegmentView_rightSegment);
        mTvLeftSegment.setText(leftSegment);
        mTvRightSegment.setText(rightSegment);
        a.recycle();
    }

    public void setOnBackSegmentClickListener(OnClickListener l) {
        mIvSegmentBack.setOnClickListener(l);
    }

    public void setOnSelectChangeListener(OnSelectChangeLitener listener) {
        if (listener != null)
            this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left_segment:
                mTvLeftSegment.setSelected(true);
                mTvRightSegment.setSelected(false);
                listener.onSelectChange(0, mTvLeftSegment);
                break;
            case R.id.tv_right_segment:
                mTvRightSegment.setSelected(true);
                mTvLeftSegment.setSelected(false);
                listener.onSelectChange(1, mTvRightSegment);
                break;
        }
    }

    public interface OnSelectChangeLitener {
        void onSelectChange(int index, TextView focusView);
    }
}
