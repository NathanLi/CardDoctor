package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 规划fragment 下拉列表按钮
 */

public class PlanSpinner extends RelativeLayout {

    private TextView mTextViewTitle;
    private ImageView mImageViewArrow;
    private View mViewPoint;

    private PopupWindow mPopupWindowList;
    private RecyclerView mRecyclerViewList;
    private DataListAdapter mListAdapter;
    private int mIndex; //当前下标
    private List<String> mSpinnerList;
    private OnItemSelectListener mSelectListener;
    private OnSpinnerClickListener mSpinnerClickListener;

    public PlanSpinner(Context context) {
        this(context, null);
    }

    public PlanSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlanSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_plan_spinner, this);
        mTextViewTitle = findViewById(R.id.text_view_title);
        mImageViewArrow = findViewById(R.id.image_view_arrow);
        mViewPoint = findViewById(R.id.view_point);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PlanSpinner);
        if (ta.hasValue(R.styleable.PlanSpinner_spinner_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.PlanSpinner_spinner_title));
        }

        initPopupWindow(context);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinnerClickListener != null) {
                    mSpinnerClickListener.onSpinnerClick();
                }
                showDown(v);
            }
        });

    }

    private void initPopupWindow(Context context) {
        mSpinnerList = new ArrayList<>();
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_plan_spinner_popup_window, null);
        mRecyclerViewList = contentView.findViewById(R.id.recycler_view);
        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(context));
        mListAdapter = new DataListAdapter(R.layout.layout_list_item_plan_spinner, mSpinnerList);
        mRecyclerViewList.setAdapter(mListAdapter);
        mPopupWindowList = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindowList.setOutsideTouchable(true);
        mPopupWindowList.setBackgroundDrawable(new BitmapDrawable());

        mListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTextViewTitle.setText(mSpinnerList.get(position));
                mIndex = position;
                mListAdapter.notifyDataSetChanged();
                mPopupWindowList.dismiss();
                if (mSelectListener != null) {
                    mSelectListener.onItemSelect(position, mSpinnerList.get(position));
                }
            }
        });

        mPopupWindowList.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mImageViewArrow.setRotation(mImageViewArrow.getRotation() == 0 ? 180 : 0);
            }
        });
    }

    public void setList(List<String> list) {
        mSpinnerList.clear();
        mSpinnerList.addAll(list);
        if (mSpinnerList.size() > 0) {
            mTextViewTitle.setText(mSpinnerList.get(0));
        }
        mListAdapter.notifyDataSetChanged();
        mIndex = 0;
    }

    //是否显示红点
    public void setPointVisibility(boolean visibility) {
        mViewPoint.setVisibility(visibility ? VISIBLE : GONE);
    }

    public void setTitle(String text) {
        mTextViewTitle.setText(text);
    }


    public void showDown(View view) {
        mImageViewArrow.setRotation(mImageViewArrow.getRotation() == 0 ? 180 : 0);
        mPopupWindowList.showAsDropDown(view);
    }


    class DataListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public DataListAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text_view_title, item);
            if (mIndex == helper.getLayoutPosition()) {
                helper.setTextColor(R.id.text_view_title, getResources().getColor(R.color.text_color_black_212c68));
                helper.getView(R.id.image_view_arrow).setVisibility(VISIBLE);
            } else {
                helper.setTextColor(R.id.text_view_title, getResources().getColor(R.color.text_color_gray_9d9d9d));
                helper.getView(R.id.image_view_arrow).setVisibility(GONE);
            }
        }
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mSelectListener = listener;
    }

    public interface OnItemSelectListener {
        void onItemSelect(int position, String text);
    }

    public void setOnSpinnerClickListener(OnSpinnerClickListener listener) {
        mSpinnerClickListener = listener;
    }

    public interface OnSpinnerClickListener {
        void onSpinnerClick();
    }


}
